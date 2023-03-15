package backend.plugfinder.repositories;

import backend.plugfinder.models.ModelBrandModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface ModelBrandRepo extends CrudRepository<ModelBrandModel, String> {
    public abstract ArrayList<ModelBrandModel> findBrandModelsByKnown(boolean know);
}
