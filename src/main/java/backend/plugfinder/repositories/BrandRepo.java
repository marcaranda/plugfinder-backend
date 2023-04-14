package backend.plugfinder.repositories;

import backend.plugfinder.repositories.entity.BrandEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface BrandRepo extends CrudRepository<BrandEntity, String> {
    @Query("SELECT b FROM BrandEntity b WHERE b.known = :known")
    public abstract ArrayList<BrandEntity> findBrandModelsByKnown(@Param("known") boolean known);
}
