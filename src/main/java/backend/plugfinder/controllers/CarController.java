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

    //region Get Methods
    @GetMapping
    public ArrayList<CarModel> getCars(){
        return carService.getCars();
    }

    //http://localhost:8080/cars/byUser?id= 'USER_ID'
    @GetMapping(path = "/byUser")
    public ArrayList<CarModel> getCarsByUserId(@RequestParam("id") long id){
        return carService.getCarsByUserId(id);
    }
    //endregion

    //region Post Methods
    @PostMapping
    public CarModel saveCar(@RequestBody CarModel carModel){
        return carService.saveCar(carModel);
    }
    //endregion

    //region Delete Methods
    @DeleteMapping(path = "/{id}")
    public String deleteCar(@PathVariable("id") String license){
        if (carService.deleteCar(license)){
            return "Se elimino correctamente el coche con matricula " + license;
        }
        else{
            return "No se ha podido eliminar el coche con matricula " + license;
        }
    }
    //endregion
}
