package backend.plugfinder.controllers;

import backend.plugfinder.models.CarModel;
import backend.plugfinder.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/cars")
public class CarController {
    @Autowired
    CarService carService;

    @GetMapping
    public ArrayList<CarModel> getCars(){
        return carService.getCars();
    }

    @PostMapping
    public CarModel saveCar(@RequestBody CarModel carModel){
        return carService.saveCar(carModel);
    }
}
