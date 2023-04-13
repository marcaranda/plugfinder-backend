package backend.plugfinder.repositories;

import backend.plugfinder.repositories.entity.ChargeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChargeRepo extends CrudRepository<ChargeEntity, String>  {

}
