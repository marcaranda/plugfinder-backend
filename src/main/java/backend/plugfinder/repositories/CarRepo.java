package backend.plugfinder.repositories;

import backend.plugfinder.models.CarModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface CarRepo extends CrudRepository<CarModel, String> {
    @Query("SELECT c FROM CarModel c WHERE c.deleted = false AND c.id.id = :user_id")
    public abstract ArrayList<CarModel> findCarModelsById_Id(@Param("user_id") long user_id);

    @Query("SELECT c FROM CarModel c WHERE c.deleted = false AND c.id.license = :license AND c.id.id = :user_id")
    public abstract Optional<CarModel> findCarModelById_LicenseAndId_Id(@Param("license") String license, @Param("user_id") long user_id);

    public abstract void deleteCarModelById_LicenseAndId_Id(String license, long user_id);
}
