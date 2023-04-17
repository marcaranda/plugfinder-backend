package backend.plugfinder.services;

import backend.plugfinder.repositories.entity.ModelBrandEntity;
import backend.plugfinder.services.models.ModelBrandModel;
import backend.plugfinder.repositories.ModelBrandRepo;
import org.modelmapper.ModelMapper;
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
        ModelMapper model_mapper = new ModelMapper();
        ArrayList<ModelBrandModel> models = new ArrayList<>();

        if (brand == null && known == null) {
            model_brand_repo.findAll().forEach(elementB -> models.add(model_mapper.map(elementB, ModelBrandModel.class)));
            return models;
        }
        else if (known == null){
            model_brand_repo.findModelBrandModelsByBrandAndKnown(brand).forEach(mb -> models.add(model_mapper.map(mb, ModelBrandModel.class)));
            return models;
        }
        else {
            if (known.equals("false")) {
                model_brand_repo.findModelBrandModelsByKnown(false).forEach(mb -> models.add(model_mapper.map(mb, ModelBrandModel.class)));
            }
            else {
                model_brand_repo.findModelBrandModelsByKnown(true).forEach(mb -> models.add(model_mapper.map(mb, ModelBrandModel.class)));
            }
            return models;
        }
    }

    public ModelBrandModel save_model(ModelBrandModel model_brand_model){
        ModelMapper model_mapper = new ModelMapper();
        return model_mapper.map(model_brand_repo.save(model_mapper.map(model_brand_model, ModelBrandEntity.class)), ModelBrandModel.class);
    }

    public ModelBrandModel get_model_by_id(String brand, String model, String autonomy){
        ModelMapper model_mapper = new ModelMapper();
        return model_mapper.map(model_brand_repo.find_model_by_id(brand, model, autonomy).get(), ModelBrandModel.class);
    }
    //endregion
}
