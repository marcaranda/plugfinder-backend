package backend.plugfinder.repositories;

import backend.plugfinder.models.BrandModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface BrandRepo extends CrudRepository<BrandModel, String> {
    @Query("SELECT b.name FROM BrandModel b WHERE b.known = true")
    public abstract ArrayList<String> findBrandModelsByKnown();
}
