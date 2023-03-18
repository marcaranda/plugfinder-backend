package backend.plugfinder.services;

import backend.plugfinder.models.BrandModel;
import backend.plugfinder.repositories.BrandRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class BrandService {
    @Autowired
    BrandRepo brand_repo;

    //region Public Methods
    public ArrayList<BrandModel> get_brands(){
        return (ArrayList<BrandModel>) brand_repo.findAll();
    }

    public BrandModel save_brand(BrandModel brandModel){
        return brand_repo.save(brandModel);
    }

    public ArrayList<String> get_brand_models_by_known(boolean know){
        return brand_repo.findBrandModelsByKnown(know);
    }
    //endregion
}
