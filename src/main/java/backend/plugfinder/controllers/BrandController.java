package backend.plugfinder.controllers;

import backend.plugfinder.controllers.dto.BrandDto;
import backend.plugfinder.services.BrandService;
import backend.plugfinder.services.models.BrandModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/brands")
public class BrandController {
    @Autowired
    BrandService brand_service;

    //region Get Methods
    @GetMapping
    public ArrayList<BrandDto> get_brands(){
        ModelMapper model_mapper = new ModelMapper();
        ArrayList<BrandDto> brands = (ArrayList<BrandDto>) brand_service.get_brands().stream()
                .map(elementB -> model_mapper.map(elementB, BrandDto.class))
                .collect(Collectors.toList());

        return brands;
    }

    //http://localhost:8080/brands/known?known=true
    @GetMapping(path = "/known")
    public ArrayList<String> get_brand_models_by_known(){
        return brand_service.get_brand_models_by_known();
    }
    //endregion

    //region Post Methods
    @PostMapping("/register")
    public BrandDto save_brand(@RequestBody BrandDto brandModel){
        ModelMapper model_mapper = new ModelMapper();
        BrandDto brand;

        if (brand_service.get_by_id(brandModel.getName()) != null) {
                brand = model_mapper.map(brand_service.get_by_id(brandModel.getName()), BrandDto.class);
        }
        else {
            brand = model_mapper.map(brand_service.save_brand(model_mapper.map(brandModel, BrandModel.class)), BrandDto.class);
        }
        return brand;
    }
    //endregion
}
