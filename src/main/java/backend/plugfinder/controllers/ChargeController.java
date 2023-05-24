package backend.plugfinder.controllers;

import backend.plugfinder.controllers.dto.ChargeDto;
import backend.plugfinder.controllers.dto.ChargerDto;
import backend.plugfinder.controllers.dto.ReservationDto;
import backend.plugfinder.helpers.OurException;
import backend.plugfinder.services.ChargeService;
import backend.plugfinder.services.ReservationService;
import backend.plugfinder.services.models.ChargeModel;
import backend.plugfinder.services.models.ChargerModel;
import backend.plugfinder.services.models.ReservationModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/charges")
public class ChargeController {
    @Autowired
    ChargeService charge_service;


    //region Get Methods
    @GetMapping
    @PreAuthorize("@securityService.not_userAPI()")
    public ArrayList<ChargeDto> get_charge(@RequestParam(required = false, value = "id") Long charge_id) throws OurException {
        ModelMapper model_mapper = new ModelMapper();
        ArrayList<ChargeDto> charges = (ArrayList<ChargeDto>) charge_service.get_charges(charge_id).stream()
                .map(elementB -> model_mapper.map(elementB, ChargeDto.class))
                .collect(Collectors.toList());

        return charges;
    }
    //endregion

    @PostMapping
    @PreAuthorize("@securityService.not_userAPI()")
    public ChargeDto save_charge(@RequestBody ChargeDto charge){
        ModelMapper model_mapper = new ModelMapper();

        return model_mapper.map(charge_service.save_charge(model_mapper.map(charge, ChargeModel.class)), ChargeDto.class);
    }

    @PostMapping(path = "/new_from_reservation")
    @PreAuthorize("@securityService.not_userAPI() && @securityService.premium_user()")
    public ChargeDto create_charge_from_reservation(@RequestParam(required = false) Long reservation_id){
        ModelMapper model_mapper = new ModelMapper();
        try {
            return model_mapper.map(charge_service.save_charge_from_reservation(reservation_id), ChargeDto.class);

        } catch (NoSuchFieldException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reservation not found");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }



    @PostMapping(path = "/{charge_id}/end")
    @PreAuthorize("@securityService.not_userAPI()")
    public ChargeDto end_charge(@PathVariable("charge_id") Long charge_id){
        ModelMapper model_mapper = new ModelMapper();

        ChargeDto charge = model_mapper.map(charge_service.get_charge(charge_id), ChargeDto.class);

        return model_mapper.map(charge_service.end_charge(model_mapper.map(charge, ChargeModel.class)), ChargeDto.class);
    }
}
