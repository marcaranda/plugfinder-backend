package backend.plugfinder.controllers;

import backend.plugfinder.controllers.dto.ChargeDto;
import backend.plugfinder.helpers.TokenValidator;
import backend.plugfinder.helpers.OurException;
import backend.plugfinder.services.ChargeService;
import backend.plugfinder.services.models.ChargeModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/charges")
public class ChargeController {
    @Autowired
    ChargeService charge_service;

    //region Get Methods
    @GetMapping
    public ArrayList<ChargeDto> get_charge(@RequestParam("id") long user_id) throws OurException {
        if (new TokenValidator().validate_id_with_token(user_id)) {
            ModelMapper model_mapper = new ModelMapper();
            ArrayList<ChargeDto> charges = (ArrayList<ChargeDto>) charge_service.get_charges_by_user_id(user_id).stream()
                    .map(elementB -> model_mapper.map(elementB, ChargeDto.class))
                    .collect(Collectors.toList());

            return charges;
        } else {
            throw new OurException("El user_id enviado es diferente al especificado en el token");
        }
    }

    @PostMapping(path = "/new")
    public ChargeDto save_charge(@RequestBody ChargeDto charge){
        ModelMapper model_mapper = new ModelMapper();
        return model_mapper.map(charge_service.save_charge(model_mapper.map(charge, ChargeModel.class)), ChargeDto.class);
    }


}
