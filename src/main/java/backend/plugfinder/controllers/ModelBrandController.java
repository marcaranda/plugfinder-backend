package backend.plugfinder.controllers;

import backend.plugfinder.models.ModelBrandModel;
import backend.plugfinder.services.ModelBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/models")
public class ModelBrandController {
    @Autowired
    ModelBrandService model_brand_service;

    //region Get Methods
    @GetMapping
    public ArrayList<ModelBrandModel> get_models(@RequestParam(required = false, value = "brand") String brand, @RequestParam(required = false, value = "known") String known){
        return model_brand_service.get_models(brand, known);
    }

    @GetMapping(path = "/{id_1}/{id_2}/{id_3}")
    public Optional<ModelBrandModel> get_model_by_id(@PathVariable("id_1") String brand, @PathVariable("id_2") String model, @PathVariable("id_3") String autonomy){
        return model_brand_service.get_model_by_id(brand, model, autonomy);
    }
    //endregion

    //region Post Methods
    @PostMapping("/register")
    public ModelBrandModel save_model(@RequestBody ModelBrandModel modelBrandModel){
        return model_brand_service.save_model(modelBrandModel);
    }
    //endregion
}
