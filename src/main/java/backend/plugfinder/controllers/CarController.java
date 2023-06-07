package backend.plugfinder.controllers;

import backend.plugfinder.controllers.dto.CarDto;
import backend.plugfinder.helpers.OurException;
import backend.plugfinder.services.CarService;
import backend.plugfinder.services.UserService;
import backend.plugfinder.services.models.CarModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
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
    @PreAuthorize("@securityService.not_userAPI()")
    public ArrayList<CarDto> get_cars(@RequestParam(required = false, value = "user_id") Long user_id) throws OurException {
        ModelMapper model_mapper = new ModelMapper();
        ArrayList<CarDto> cars = (ArrayList<CarDto>) car_service.get_cars(user_id).stream()
                .map(elementB -> model_mapper.map(elementB, CarDto.class))
                .collect(Collectors.toList());

        return cars;
    }

    @GetMapping(path = "/license/{license}/user_id/{user_id}")
    @PreAuthorize("@securityService.not_userAPI()")
    public CarDto get_car_by_id(@PathVariable("license") String license, @PathVariable("user_id") long user_id) throws OurException {
        ModelMapper model_mapper = new ModelMapper();
        return model_mapper.map(car_service.get_car_by_id(license, user_id), CarDto.class);
    }

    @GetMapping(path = "user_id/{user_id}/default")
    @PreAuthorize("@securityService.not_userAPI()")
    public CarDto get_default_car(@PathVariable("user_id") long user_id) throws OurException {
        ModelMapper model_mapper = new ModelMapper();
        return model_mapper.map(car_service.get_default_car(user_id), CarDto.class);
    }
    //endregion

    //region Post Methods
    @PostMapping
    @PreAuthorize("@securityService.not_userAPI()")
    public CarDto save_car(@RequestBody CarDto car_dto) throws OurException {
        ModelMapper model_mapper = new ModelMapper();

        return model_mapper.map(car_service.save_car(model_mapper.map(car_dto, CarModel.class)), CarDto.class);
    }
    //endregion

    //region Put Methods
    @PutMapping
    @PreAuthorize("@securityService.not_userAPI()")
    public CarDto edit_car(@RequestBody CarDto car_dto) throws OurException {
        ModelMapper model_mapper = new ModelMapper();

        return model_mapper.map(car_service.save_car(model_mapper.map(car_dto, CarModel.class)), CarDto.class);
    }

    @PutMapping(path = "/license/{license}/user_id/{user_id}/default")
    @PreAuthorize("@securityService.not_userAPI()")
    public CarDto set_default_car(@PathVariable("license") String license, @PathVariable("user_id") long user_id) throws OurException {
        ModelMapper model_mapper = new ModelMapper();

        return model_mapper.map(car_service.default_car(license, user_id), CarDto.class);
    }
    //endregion

    //region Delete Methods
    @DeleteMapping(path = "/license/{license}/user_id/{user_id}")
    @PreAuthorize("@securityService.not_userAPI()")
    public String delete_car(@PathVariable("license") String license, @PathVariable("user_id") long user_id) throws OurException {
        return car_service.delete_car(license, user_id);
    }
    //endregion
}
