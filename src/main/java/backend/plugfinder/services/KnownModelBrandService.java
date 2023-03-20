package backend.plugfinder.services;

import backend.plugfinder.models.KnownModelBrandModel;
import backend.plugfinder.repositories.KnownModelBrandRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class KnownModelBrandService {
    @Autowired
    KnownModelBrandRepo model_brand_repo;

    //region Public Methods
    public ArrayList<KnownModelBrandModel> get_models(){
        return (ArrayList<KnownModelBrandModel>) model_brand_repo.findAll();
    }

    public KnownModelBrandModel save_model(KnownModelBrandModel knownModelBrandModel){
        return model_brand_repo.save(knownModelBrandModel);
    }

    public ArrayList<KnownModelBrandModel> get_model_brand_models_by_known(){
        return model_brand_repo.findModelBrandModelsByKnown(true);
    }

    public ArrayList<String> get_model_brand_model_by_brand_and_known(String brand){
        return model_brand_repo.findModelBrandModelsByBrandAndKnown(brand);
    }
    //endregion
}
