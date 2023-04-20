package backend.plugfinder.controllers;

import backend.plugfinder.controllers.dto.ChargeDto;
import backend.plugfinder.controllers.dto.ChargerDto;
import backend.plugfinder.helpers.OurException;
import backend.plugfinder.services.ChargeService;
import backend.plugfinder.services.ChargerService;
import backend.plugfinder.services.models.ChargeModel;
import backend.plugfinder.services.models.ChargerModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/charges")
public class ChargeController {
    @Autowired
    ChargeService charge_service;
    @Autowired
    ChargerService charger_service;

    //region Get Methods
    @GetMapping
    public ArrayList<ChargeDto> get_charge(@RequestParam(required = false, value = "id") Long user_id) throws OurException {
        ModelMapper model_mapper = new ModelMapper();
        ArrayList<ChargeDto> charges = (ArrayList<ChargeDto>) charge_service.get_charges(user_id).stream()
                .map(elementB -> model_mapper.map(elementB, ChargeDto.class))
                .collect(Collectors.toList());

        return charges;
    }
    //endregion

    @PostMapping
    public ChargeDto save_charge(@RequestBody ChargeDto charge){
        ModelMapper model_mapper = new ModelMapper();
        ChargerDto charger = charge.getCharger();
        try{
            charger_service.new_charge(model_mapper.map(charger, ChargerModel.class));
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Charger is not free");
        }

        return model_mapper.map(charge_service.save_charge(model_mapper.map(charge, ChargeModel.class)), ChargeDto.class);
    }

    @PostMapping(path = "/{charge_id}/end")
    public ChargeDto end_charge(@PathVariable("charge_id") Long charge_id){
        ModelMapper model_mapper = new ModelMapper();

        ChargeDto charge = model_mapper.map(charge_service.get_charge(charge_id), ChargeDto.class);
        ChargerDto charger = charge.getCharger();
        try{
            charger_service.end_charge(model_mapper.map(charger, ChargerModel.class));
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Charger is not free");
        }

        return model_mapper.map(charge_service.end_charge(model_mapper.map(charge, ChargeModel.class)), ChargeDto.class);
    }
}
