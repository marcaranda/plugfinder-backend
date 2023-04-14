package backend.plugfinder.controllers;

import backend.plugfinder.controllers.dto.ModelBrandDto;
import backend.plugfinder.services.ModelBrandService;
import backend.plugfinder.services.models.ModelBrandModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/models")
public class ModelBrandController {
    @Autowired
    ModelBrandService model_brand_service;

    //region Get Methods
    @GetMapping
    public ArrayList<ModelBrandDto> get_models(@RequestParam(required = false, value = "brand") String brand, @RequestParam(required = false, value = "known") String known){
        ModelMapper model_mapper = new ModelMapper();
        ArrayList<ModelBrandDto> models = (ArrayList<ModelBrandDto>) model_brand_service.get_models(brand, known).stream()
                .map(elementB -> model_mapper.map(elementB, ModelBrandDto.class))
                .collect(Collectors.toList());

        return models;
    }

    @GetMapping(path = "/brand/{brand}/model/{model}/autonomy/{autonomy}")
    public ModelBrandDto get_model_by_id(@PathVariable("brand") String brand, @PathVariable("model") String model, @PathVariable("autonomy") String autonomy){
        ModelMapper model_mapper = new ModelMapper();

        return model_mapper.map(model_brand_service.get_model_by_id(brand, model, autonomy), ModelBrandDto.class);
    }
    //endregion

    //region Post Methods
    @PostMapping
    public ModelBrandDto save_model(@RequestBody ModelBrandDto modelBrandDto){
        ModelMapper model_mapper = new ModelMapper();

        return model_mapper.map(model_brand_service.save_model(model_mapper.map(modelBrandDto, ModelBrandModel.class)), ModelBrandDto.class);
    }
    //endregion
}
