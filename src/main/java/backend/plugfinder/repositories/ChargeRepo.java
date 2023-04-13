package backend.plugfinder.repositories;

import backend.plugfinder.repositories.entity.ChargeEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface ChargeRepo extends CrudRepository<ChargeEntity, Long>  {
    @Query("SELECT cm FROM ChargeEntity cm WHERE cm.car.id.id = :user_id")
    ArrayList<ChargeEntity> get_charges_by_user_id(@Param("user_id") long user_id);
}
