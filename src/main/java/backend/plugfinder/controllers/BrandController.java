package backend.plugfinder.controllers;

import backend.plugfinder.controllers.dto.BrandDto;
import backend.plugfinder.services.BrandService;
import backend.plugfinder.services.models.BrandModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/brands")
public class BrandController {
    @Autowired
    BrandService brand_service;

    //region Get Methods
    @GetMapping
    public ArrayList<BrandDto> get_brands(@RequestParam(required = false, value = "known") String known){
        ModelMapper model_mapper = new ModelMapper();
        ArrayList<BrandDto> brands = (ArrayList<BrandDto>) brand_service.get_brands(known).stream()
                .map(elementB -> model_mapper.map(elementB, BrandDto.class))
                .collect(Collectors.toList());

        return brands;
    }
    //endregion

    //region Post Methods
    @PostMapping
    public BrandDto save_brand(@RequestBody BrandDto brand_model){
        ModelMapper model_mapper = new ModelMapper();
        return model_mapper.map(brand_service.save_brand(model_mapper.map(brand_model<2, BrandModel.class)), BrandDto.class);
    }
    //endregion
}
