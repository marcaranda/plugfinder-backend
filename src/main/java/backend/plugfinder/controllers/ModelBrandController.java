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
    ModelBrandService modelBrandService;

    //region Get Methods
    @GetMapping
    public ArrayList<ModelBrandModel> getModels(){
        return modelBrandService.getModels();
    }

    @GetMapping(path = "/query")
    public ArrayList<ModelBrandModel> getModelBrandModelsByKnown(@RequestParam("known") boolean known){
        return modelBrandService.getModelBrandModelsByKnown(known);
    }

    //http://localhost:8080/models/byBrand?brand='NAME_BRAND'&known=true
    @GetMapping(path = "/byBrand")
    public ArrayList<String> getModelBrandModelsByBrandAndKnown(@RequestParam("brand") String brand, @RequestParam("known") boolean known){
        return modelBrandService.getModelBrandModelByBrandAndKnown(brand, known);
    }
    //endregion

    //region Post Methods
    @PostMapping("/register")
    public ModelBrandModel saveModel(@RequestBody ModelBrandModel modelBrandModel){
        return modelBrandService.saveModel(modelBrandModel);
    }
    //endregion
}
