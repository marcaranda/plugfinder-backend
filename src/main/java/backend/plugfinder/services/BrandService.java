package backend.plugfinder.services;

import backend.plugfinder.controllers.dto.BrandDto;
import backend.plugfinder.services.models.BrandModel;
import backend.plugfinder.repositories.BrandRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BrandService {
    @Autowired
    BrandRepo brand_repo;

    //region Public Methods
    public ArrayList<BrandModel> get_brands(){
        ModelMapper modelMapper = new ModelMapper();
        ArrayList<BrandModel> brands = new ArrayList<>();
        brand_repo.findAll().forEach(b -> brands.add(modelMapper.map(b, BrandModel.class)));
        return brands;
    }

    public BrandModel save_brand(BrandModel brandModel){
        ModelMapper modelMapper = new ModelMapper();
        BrandModel brand = modelMapper.map(brand_repo.save(brandModel), BrandModel.class);
        return brand;
    }

    public ArrayList<String> get_brand_models_by_known(){
        return brand_repo.findBrandModelsByKnown();
    }

    public BrandModel get_by_id(String name) {
        ModelMapper modelMapper = new ModelMapper();
        BrandModel brand = modelMapper.map(brand_repo.findById(name).get(), BrandModel.class);
        return brand;
    }
    //endregion
}
