package backend.plugfinder.repositories;

import backend.plugfinder.models.ChargeModel;
import org.springframework.data.repository.CrudRepository;

public interface ChargeRepo extends CrudRepository<ChargeModel, String>  {

}
