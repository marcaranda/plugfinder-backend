package backend.plugfinder.controllers;

import backend.plugfinder.helpers.TokenValidator;
import backend.plugfinder.models.CarModel;
import backend.plugfinder.models.OurException;
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
    @GetMapping(path = "/{id_1}/{id_2}")
    public Optional<CarModel> get_car_by_id(@PathVariable("id_1") String license, @PathVariable("id_2") long user_id) throws OurException {
        if (new TokenValidator().validate_id_with_token(user_id)) {
            return car_service.get_car_by_id(license, user_id);
        }
        else {
            throw new OurException("El user_id enviado es diferente al especificado en el token");
        }
    }

    //http://localhost:8080/cars/byUser?id='USER_ID'
    @GetMapping(path = "/byUser")
    public ArrayList<CarModel> get_cars_by_user_id(@RequestParam("id") long user_id) throws OurException {
        if (new TokenValidator().validate_id_with_token(user_id)) {
            return car_service.get_cars_by_user_id(user_id);
        }
        else {
            throw new OurException("El user_id enviado es diferente al especificado en el token");
        }
    }
    //endregion

    //region Post Methods
    @PostMapping("/register")
    public CarModel save_car(@RequestBody CarModel car_Model) throws SQLException {
        /* Comprobación validez matrícula */
            if (!validate_license(car_Model.getId().getLicense())){
            throw new SQLException("La matrícula no es válida");
        }
        /* Comprobación validez usuario */
        if (!car_Model.getModel_brand_model().isKnown()){
            if (car_Model.getModel_brand_model().getId().getUser_id() != car_Model.getUser_model().getUser_id()){
                throw new SQLException("El coche no es válido");
            }
        }

        return car_service.save_car(car_Model);
    }

    @PostMapping("/edit")
    public CarModel edit_car(@RequestBody CarModel car_Model) throws SQLException {
        return car_service.save_car(car_Model);
    }
    //endregion

    //region Delete Methods
    //http://localhost:8080/cars/delete/'LICENSE'-'USER_ID'
    @DeleteMapping(path = "/delete/{id_1}/{id_2}")
    public String delete_car(@PathVariable("id_1") String license, @PathVariable("id_2") long user_id) throws OurException {
        if (new TokenValidator().validate_id_with_token(user_id)) {
            Optional<CarModel> car_model = car_service.get_car_by_id(license, user_id);
            if (car_model.isPresent()) {
                CarModel car = car_model.get();
                car.setDeleted(true);
                car.setUser_model(null);
                car_service.save_car(car);
                return "Se elimino correctamente el coche con matricula " + license;
            } else {
                return "No se ha podido eliminar el coche con matricula " + license;
            }
        }
        else {
            throw new OurException("El user_id enviado es diferente al especificado en el token");
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
    //endregion
}
