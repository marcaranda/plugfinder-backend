package backend.plugfinder.controllers;

import backend.plugfinder.models.BrandModel;
import backend.plugfinder.services.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/brands")
public class BrandController {
    @Autowired
    BrandService brand_service;

    //region Get Methods
    @GetMapping
    public ArrayList<BrandModel> get_brands(){
        return brand_service.get_brands();
    }

    //http://localhost:8080/brands/known?known=true
    @GetMapping(path = "/known")
    public ArrayList<String> get_brand_models_by_known(){
        return brand_service.get_brand_models_by_known();
    }
    //endregion

    //region Post Methods
    @PostMapping("/register")
    public BrandModel save_brand(@RequestBody BrandModel brandModel){
        return brand_service.save_brand(brandModel);
    }
    //endregion
}