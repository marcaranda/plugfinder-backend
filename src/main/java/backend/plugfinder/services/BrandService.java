package backend.plugfinder.services;

import backend.plugfinder.models.BrandModel;
import backend.plugfinder.repositories.BrandRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class BrandService {
    @Autowired
    BrandRepo brandRepo;

    //region Public Methods
    public ArrayList<BrandModel> getsBrands(){
        return (ArrayList<BrandModel>) brandRepo.findAll();
    }

    public BrandModel saveBrand(BrandModel brandModel){
        return brandRepo.save(brandModel);
    }

    public ArrayList<String> getBrandModelsByKnown(boolean know){
        return brandRepo.findBrandModelsByKnown(know);
    }
    //endregion
}
