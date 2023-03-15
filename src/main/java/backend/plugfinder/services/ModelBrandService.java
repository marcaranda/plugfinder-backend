package backend.plugfinder.services;

import backend.plugfinder.models.ModelBrandModel;
import backend.plugfinder.repositories.ModelBrandRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ModelBrandService {
    @Autowired
    ModelBrandRepo modelBrandRepo;

    public ArrayList<ModelBrandModel> getModels(){
        return (ArrayList<ModelBrandModel>) modelBrandRepo.findAll();
    }

    public ModelBrandModel saveModel(ModelBrandModel modelBrandModel){
        return modelBrandRepo.save(modelBrandModel);
    }

    public ArrayList<ModelBrandModel> getBrandModelsByKnown(boolean know){
        return modelBrandRepo.findBrandModelsByKnown(know);
    }
}
