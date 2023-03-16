package backend.plugfinder.repositories;

import backend.plugfinder.models.CarModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface CarRepo extends CrudRepository<CarModel, String> {
    public abstract ArrayList<CarModel> findCarModelsById_Id(long id);
}
