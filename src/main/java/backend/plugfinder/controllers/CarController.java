package backend.plugfinder.controllers;

import backend.plugfinder.controllers.dto.CarDto;
import backend.plugfinder.helpers.TokenValidator;
import backend.plugfinder.helpers.OurException;
import backend.plugfinder.services.CarService;
import backend.plugfinder.services.UserService;
import backend.plugfinder.services.models.CarModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cars")
public class CarController {
    @Autowired
    CarService car_service;
    @Autowired
    UserService user_service;

    //region Get Methods
    @GetMapping
    public ArrayList<CarDto> get_cars(){
        ModelMapper model_mapper = new ModelMapper();
        ArrayList<CarDto> cars = (ArrayList<CarDto>) car_service.get_cars().stream()
                .map(elementB -> model_mapper.map(elementB, CarDto.class))
                .collect(Collectors.toList());

        return cars;
    }

    //http://localhost:8080/cars/'LICENSE'-'USER_ID'
    @GetMapping(path = "/{id_1}/{id_2}")
    public CarDto get_car_by_id(@PathVariable("id_1") String license, @PathVariable("id_2") long user_id) throws OurException {
        if (new TokenValidator().validate_id_with_token(user_id)) {
            ModelMapper model_mapper = new ModelMapper();
            return model_mapper.map(car_service.get_car_by_id(license, user_id), CarDto.class);
        }
        else {
            throw new OurException("El user_id enviado es diferente al especificado en el token");
        }
    }

    //http://localhost:8080/cars/byUser?id='USER_ID'
    @GetMapping(path = "/byUser")
    public ArrayList<CarDto> get_cars_by_user_id(@RequestParam("id") long user_id) throws OurException {
        if (new TokenValidator().validate_id_with_token(user_id)) {
            ModelMapper model_mapper = new ModelMapper();
            ArrayList<CarDto> cars = (ArrayList<CarDto>) car_service.get_cars_by_user_id(user_id).stream()
                    .map(elementB -> model_mapper.map(elementB, CarDto.class))
                    .collect(Collectors.toList());
            return cars;
        }
        else {
            throw new OurException("El user_id enviado es diferente al especificado en el token");
        }
    }
    //endregion

    //region Post Methods
    @PostMapping("/register")
    public CarDto save_car(@RequestBody CarDto car_dto) throws SQLException {
        ModelMapper model_mapper = new ModelMapper();

        return model_mapper.map(car_service.save_car(model_mapper.map(car_dto, CarModel.class)), CarDto.class);
    }

    @PostMapping("/edit")
    public CarDto edit_car(@RequestBody CarDto car_dto) throws SQLException {
        ModelMapper model_mapper = new ModelMapper();

        return model_mapper.map(car_service.save_car(model_mapper.map(car_dto, CarModel.class)), CarDto.class);
    }
    //endregion

    //region Delete Methods
    //http://localhost:8080/cars/delete/'LICENSE'-'USER_ID'
    @DeleteMapping(path = "/delete/{id_1}/{id_2}")
    public String delete_car(@PathVariable("id_1") String license, @PathVariable("id_2") long user_id) throws OurException {
        if (new TokenValidator().validate_id_with_token(user_id)) {
            return car_service.delete_car(license, user_id);
        }
        else {
            throw new OurException("El user_id enviado es diferente al especificado en el token");
        }
    }
    //endregion
}
