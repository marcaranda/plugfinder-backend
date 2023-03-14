package backend.plugfinder.repositories;

import backend.plugfinder.models.CarModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepo extends CrudRepository<CarModel, String> {
}
