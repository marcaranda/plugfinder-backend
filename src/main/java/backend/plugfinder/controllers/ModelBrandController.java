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

    @GetMapping
    public ArrayList<ModelBrandModel> getModels(){
        return modelBrandService.getModels();
    }

    @PostMapping
    public ModelBrandModel saveModel(@RequestBody ModelBrandModel modelBrandModel){
        return modelBrandService.saveModel(modelBrandModel);
    }

    @GetMapping(path = "/query")
    public ArrayList<ModelBrandModel> getBrandModelsByKnown(@RequestParam("known") boolean known){
        return modelBrandService.getBrandModelsByKnown(known);
    }
}
