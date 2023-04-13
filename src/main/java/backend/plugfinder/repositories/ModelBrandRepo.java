package backend.plugfinder.repositories;

import backend.plugfinder.repositories.entity.ModelBrandEntity;
import backend.plugfinder.helpers.ModelBrandId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface ModelBrandRepo extends CrudRepository<ModelBrandEntity, ModelBrandId> {
    public abstract ArrayList<ModelBrandEntity> findModelBrandModelsByKnown(boolean know);

    @Query("SELECT mb FROM ModelBrandEntity mb WHERE mb.id.brand_name = :brand AND mb.known = true")
    public abstract ArrayList<ModelBrandEntity> findModelBrandModelsByBrandAndKnown(@Param("brand") String brand);

    @Query("SELECT mb FROM ModelBrandEntity mb WHERE mb.id.brand_name = :brand AND mb.id.name = :model AND mb.id.autonomy = :autonomy")
    public abstract Optional<ModelBrandEntity> find_model_by_id(@Param("brand") String brand, @Param("model") String model, @Param("autonomy") String autonomy);
}
