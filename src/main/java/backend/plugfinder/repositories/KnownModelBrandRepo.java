package backend.plugfinder.repositories;

import backend.plugfinder.models.KnownModelBrandModel;
import backend.plugfinder.helpersId.KnownModelBrandId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface KnownModelBrandRepo extends CrudRepository<KnownModelBrandModel, KnownModelBrandId> {
    public abstract ArrayList<KnownModelBrandModel> findModelBrandModelsByKnown(boolean know);

    @Query("SELECT mb.id.name FROM KnownModelBrandModel mb WHERE mb.id.brand_name = :brand AND mb.known = true")
    public abstract ArrayList<String> findModelBrandModelsByBrandAndKnown(@Param("brand") String brand);
}
