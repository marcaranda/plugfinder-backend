package backend.plugfinder.repositories;

import backend.plugfinder.helpers.CarId;
import backend.plugfinder.repositories.entity.CarEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface CarRepo extends CrudRepository<CarEntity, CarId> {
    @Query("SELECT c FROM CarEntity c WHERE c.deleted = false AND c.id.id = :user_id")
    public abstract ArrayList<CarEntity> findCarModelsById_Id(@Param("user_id") long user_id);

    @Query("SELECT c FROM CarEntity c WHERE c.deleted = false AND c.id.license = :license AND c.id.id = :user_id")
    public abstract Optional<CarEntity> findCarModelById_LicenseAndId_Id(@Param("license") String license, @Param("user_id") long user_id);

    @Query("SELECT c FROM CarEntity c WHERE c.default_car = true AND c.id.id = :user_id")
    public abstract Optional<CarEntity> findDefaultCarByUserId(@Param("user_id") long userId);
}
