package backend.plugfinder.repositories;

import backend.plugfinder.models.ModelBrandModel;
import backend.plugfinder.helpers.ModelBrandId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface ModelBrandRepo extends CrudRepository<ModelBrandModel, ModelBrandId> {
    public abstract ArrayList<ModelBrandModel> findModelBrandModelsByKnown(boolean know);

    @Query("SELECT mb.id.name FROM ModelBrandModel mb WHERE mb.id.brand_name = :brand AND mb.known = true")
    public abstract ArrayList<String> findModelBrandModelsByBrandAndKnown(@Param("brand") String brand);
}
