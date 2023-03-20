package backend.plugfinder.controllers;

import backend.plugfinder.models.ModelBrandModel;
import backend.plugfinder.services.ModelBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/models")
public class ModelBrandController {
    @Autowired
    ModelBrandService model_brand_service;

    //region Get Methods
    @GetMapping
    public ArrayList<ModelBrandModel> get_models(){
        return model_brand_service.get_models();
    }

    @GetMapping(path = "/known")
    public ArrayList<ModelBrandModel> get_model_brand_models_by_known(){
        return model_brand_service.get_model_brand_models_by_known();
    }

    //http://localhost:8080/models/byBrand?brand='NAME_BRAND'&known=true
    @GetMapping(path = "/byBrand")
    public ArrayList<String> get_model_brand_model_by_brand_and_known(@RequestParam("brand") String brand){
        return model_brand_service.get_model_brand_model_by_brand_and_known(brand);
    }
    //endregion

    //region Post Methods
    @PostMapping("/register")
    public ModelBrandModel save_model(@RequestBody ModelBrandModel modelBrandModel){
        return model_brand_service.save_model(modelBrandModel);
    }
    //endregion
}
