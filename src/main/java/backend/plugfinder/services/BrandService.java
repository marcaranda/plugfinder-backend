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
    public ArrayList<BrandModel> get_brands(String known){
        ModelMapper model_mapper = new ModelMapper();
        ArrayList<BrandModel> brands = new ArrayList<>();

        if (known != null){
            boolean know;
            if (known.equals("false")) know = false;
            else know = true;

            brand_repo.findBrandModelsByKnown(know).forEach(elementB -> brands.add(model_mapper.map(elementB, BrandModel.class)));
        }
        else brand_repo.findAll().forEach(elementB -> brands.add(model_mapper.map(elementB, BrandModel.class)));
        return brands;
    }

    public BrandModel save_brand(BrandModel brand_model){
        ModelMapper model_mapper = new ModelMapper();
        BrandModel brand;
        if (brand_repo.findById(brand_model.getName()).isPresent()) brand = model_mapper.map(brand_repo.findById(brand_model.getName()).get(), BrandModel.class);
        else brand = null;

        if (brand != null) {
            return brand;
        }
        else {
           return model_mapper.map(brand_repo.save(model_mapper.map(brand_model, BrandEntity.class)), BrandModel.class);
        }
    }

    public BrandModel get_by_id(String name) {
        ModelMapper model_mapper = new ModelMapper();
        return model_mapper.map(brand_repo.findById(name).get(), BrandModel.class);
    }
    //endregion
}
