package backend.plugfinder.repositories;

import backend.plugfinder.repositories.entity.BrandEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface BrandRepo extends CrudRepository<BrandEntity, String> {
    @Query("SELECT b.name FROM BrandEntity b WHERE b.known = true")
    public abstract ArrayList<String> findBrandModelsByKnown();
}
