package backend.plugfinder.services;

import backend.plugfinder.models.ModelBrandModel;
import backend.plugfinder.repositories.ModelBrandRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ModelBrandService {
    @Autowired
    ModelBrandRepo model_brand_repo;

    //region Public Methods
    public ArrayList<ModelBrandModel> get_models(){
        return (ArrayList<ModelBrandModel>) model_brand_repo.findAll();
    }

    public ModelBrandModel save_model(ModelBrandModel modelBrandModel){
        return model_brand_repo.save(modelBrandModel);
    }

    public ArrayList<ModelBrandModel> get_model_brand_models_by_known(){
        return model_brand_repo.findModelBrandModelsByKnown(true);
    }

    public ArrayList<String> get_model_brand_model_by_brand_and_known(String brand){
        return model_brand_repo.findModelBrandModelsByBrandAndKnown(brand);
    }
    //endregion
}
