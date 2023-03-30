package backend.plugfinder.services;

import backend.plugfinder.models.ModelBrandModel;
import backend.plugfinder.repositories.ModelBrandRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class ModelBrandService {
    @Autowired
    ModelBrandRepo model_brand_repo;

    //region Public Methods
    public ArrayList<ModelBrandModel> get_models(String brand, String known){
        if (brand == null && known == null) {
            return (ArrayList<ModelBrandModel>) model_brand_repo.findAll();
        }
        else if (known == null){
            return model_brand_repo.findModelBrandModelsByBrandAndKnown(brand);
        }
        else {
            if (known.equals("false"))
                return model_brand_repo.findModelBrandModelsByKnown(false);
            else
                return model_brand_repo.findModelBrandModelsByKnown(true);
        }
    }

    public ModelBrandModel save_model(ModelBrandModel modelBrandModel){
        return model_brand_repo.save(modelBrandModel);
    }

    public Optional<ModelBrandModel> get_model_by_id(String brand, String model, String autonomy){
        return model_brand_repo.find_model_by_id(brand, model, autonomy);
    }
    //endregion
}
