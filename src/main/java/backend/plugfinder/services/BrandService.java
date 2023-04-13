package backend.plugfinder.services;

import backend.plugfinder.repositories.entity.BrandEntity;
import backend.plugfinder.services.models.BrandModel;
import backend.plugfinder.repositories.BrandRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class BrandService {
    @Autowired
    BrandRepo brand_repo;

    //region Public Methods
    public ArrayList<BrandModel> get_brands(){
        ModelMapper model_mapper = new ModelMapper();
        ArrayList<BrandModel> brands = new ArrayList<>();
        brand_repo.findAll().forEach(elementB -> brands.add(model_mapper.map(elementB, BrandModel.class)));
        return brands;
    }

    public BrandModel save_brand(BrandModel brandModel){
        ModelMapper model_mapper = new ModelMapper();
        return model_mapper.map(brand_repo.save(model_mapper.map(brandModel, BrandEntity.class)), BrandModel.class);
    }

    public ArrayList<String> get_brand_models_by_known(){
        return brand_repo.findBrandModelsByKnown();
    }

    public BrandModel get_by_id(String name) {
        ModelMapper model_mapper = new ModelMapper();
        return model_mapper.map(brand_repo.findById(name).get(), BrandModel.class);
    }
    //endregion
}
