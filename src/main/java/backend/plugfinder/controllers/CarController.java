package backend.plugfinder.controllers;

import backend.plugfinder.models.CarModel;
import backend.plugfinder.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/cars")
public class CarController {
    @Autowired
    CarService car_service;

    //region Get Methods
    @GetMapping
    public ArrayList<CarModel> get_cars(){
        return car_service.get_cars();
    }

    //http://localhost:8080/cars/'LICENSE'-'USER_ID'
    @GetMapping(path = "/{id_1}-{id_2}")
    public Optional<CarModel> get_car_by_id(@PathVariable("id_1") String license, @PathVariable("id_2") long user_id){
        return car_service.get_car_by_id(license, user_id);
    }

    //http://localhost:8080/cars/byUser?id='USER_ID'
    @GetMapping(path = "/byUser")
    public ArrayList<CarModel> get_cars_by_user_id(@RequestParam("id") long user_id){
        return car_service.get_cars_by_user_id(user_id);
    }
    //endregion

    //region Post Methods
    @PostMapping("/register")
    public CarModel save_car(@RequestBody CarModel car_Model) throws SQLException {
        /* Comprobación validez matrícula */
            if (!validate_license(car_Model.getId().getLicense())){
            throw new SQLException("La matrícula no es válida");
        }
        /* Comprobación validez autonomía */
        if (!validate_autonomy(car_Model.getAutonomy())){
            throw new SQLException("La autonomía no es válida");
        }
        return car_service.save_car(car_Model);
    }
    //endregion

    //region Delete Methods
    //http://localhost:8080/cars/delete/'LICENSE'-'USER_ID'
    @DeleteMapping(path = "/delete/{id_1}-{id_2}")
    public String delete_car(@PathVariable("id_1") String license, @PathVariable("id_2") long user_id){
        if (car_service.delete_car(license, user_id)){
            return "Se elimino correctamente el coche con matricula " + license;
        }
        else{
            return "No se ha podido eliminar el coche con matricula " + license;
        }
    }
    //endregion

    //region Private Methods
    private boolean validate_license(String license){
        String patron = "^\\d{4}[BCDFGHJKLMNÑPQRSTVWXYZ]{3}$";
        Pattern pattern = Pattern.compile(patron);
        Matcher matcher = pattern.matcher(license);
        return matcher.matches();
    }

    private boolean validate_autonomy(String autonomy){
        String patron = "^\\d{2,4}$";
        Pattern pattern = Pattern.compile(patron);
        Matcher matcher = pattern.matcher(autonomy);
        return matcher.matches();
    }
    //endregion
}
