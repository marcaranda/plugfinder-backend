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
    BrandService brandService;

    //region Get Methods
    @GetMapping
    public ArrayList<BrandModel> getBrands(){
        return brandService.getsBrands();
    }

    //http://localhost:8080/brands/known?known=true
    @GetMapping(path = "/known")
    public ArrayList<String> getBrandModelsByKnown(@RequestParam("known") boolean known){
        return brandService.getBrandModelsByKnown(known);
    }
    //endregion

    //region Post Methods
    @PostMapping("/register")
    public BrandModel saveModel(@RequestBody BrandModel brandModel){
        return brandService.saveBrand(brandModel);
    }
    //endregion
}
