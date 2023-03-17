package backend.plugfinder.repositories;

import backend.plugfinder.models.CarModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface CarRepo extends CrudRepository<CarModel, String> {
    public abstract ArrayList<CarModel> findCarModelsById_Id(long user_id);

    public abstract Optional<CarModel> findCarModelById_LicenseAndId_Id(String license, long user_id);

    public abstract void deleteCarModelById_LicenseAndId_Id(String license, long user_id);
}
