package backend.plugfinder.repositories;

import backend.plugfinder.models.BrandModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface BrandRepo extends CrudRepository<BrandModel, String> {
    public abstract ArrayList<BrandModel> findBrandModelsByKnown(boolean know);
}
