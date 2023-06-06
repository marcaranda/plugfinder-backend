package backend.plugfinder.repositories;

import backend.plugfinder.repositories.entity.ChargerEntity;
import backend.plugfinder.repositories.entity.ChargerTypeEntity;
import jakarta.persistence.criteria.*;
import org.apache.commons.lang3.tuple.Pair;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;


public class ChargerSpecification implements Specification<ChargerEntity> {

    private SearchCriteria criteria;

    public ChargerSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate
            (Root<ChargerEntity> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        if (criteria.getOperation().equalsIgnoreCase(">")) {
            return builder.greaterThanOrEqualTo(
                    root.<String> get(criteria.getKey()), criteria.getValue().toString());
        }
        else if (criteria.getOperation().equalsIgnoreCase("<")) {
            return builder.lessThanOrEqualTo(
                    root.<String> get(criteria.getKey()), criteria.getValue().toString());
        }
        else if (criteria.getOperation().equalsIgnoreCase(":")) {

            if (criteria.getKey()=="type_id"){

                List<Long> longList = (List<Long>) criteria.getValue();

                Join<ChargerEntity, ChargerTypeEntity> typeJoin = root.join("types");
                return typeJoin.get("type_id").in(longList);

            } else if (root.get(criteria.getKey()).getJavaType() == String.class) {
                return builder.like(
                        root.<String>get(criteria.getKey()), "%" + criteria.getValue() + "%");
            } else {

                return builder.equal(root.get(criteria.getKey()), criteria.getValue());
            }
        }
        else if (criteria.getOperation().equalsIgnoreCase("between")){
            return builder.between(root.get(criteria.getKey()), (int) criteria.getValue(), (int) criteria.getValue2());

        }
        return null;
    }
}