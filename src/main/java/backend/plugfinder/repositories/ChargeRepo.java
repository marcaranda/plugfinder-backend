package backend.plugfinder.repositories;

import backend.plugfinder.models.ChargeModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChargeRepo extends CrudRepository<ChargeModel, Long>  {

}
