package backend.plugfinder.controllers;

import backend.plugfinder.models.KnownModelBrandModel;
import backend.plugfinder.services.KnownModelBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/models")
public class KnownModelBrandController {
    @Autowired
    KnownModelBrandService model_brand_service;

    //region Get Methods
    @GetMapping
    public ArrayList<KnownModelBrandModel> get_models(){
        return model_brand_service.get_models();
    }

    @GetMapping(path = "/known")
    public ArrayList<KnownModelBrandModel> get_model_brand_models_by_known(@RequestParam("known") boolean known){
        return model_brand_service.get_model_brand_models_by_known(known);
    }

    //http://localhost:8080/models/byBrand?brand='NAME_BRAND'&known=true
    @GetMapping(path = "/byBrand")
    public ArrayList<String> get_model_brand_model_by_brand_and_known(@RequestParam("brand") String brand, @RequestParam("known") boolean known){
        return model_brand_service.get_model_brand_model_by_brand_and_known(brand, known);
    }
    //endregion

    //region Post Methods
    @PostMapping("/register")
    public KnownModelBrandModel save_model(@RequestBody KnownModelBrandModel knownModelBrandModel){
        return model_brand_service.save_model(knownModelBrandModel);
    }
    //endregion
}
