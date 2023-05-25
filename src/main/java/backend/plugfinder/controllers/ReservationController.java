package backend.plugfinder.controllers;

import backend.plugfinder.controllers.dto.ChargerDto;
import backend.plugfinder.controllers.dto.ReservationDto;
import backend.plugfinder.helpers.OurException;
import backend.plugfinder.services.ChargeService;
import backend.plugfinder.services.ChargerService;
import backend.plugfinder.services.ReservationService;
import backend.plugfinder.services.models.ChargerModel;
import backend.plugfinder.services.models.ReservationModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    @Autowired
    ReservationService reservation_service;

    //region Get Methods
    @GetMapping
    @PreAuthorize("@securityService.not_userAPI() && @securityService.premium_user()")
    public ArrayList<ReservationDto> get_reservation(@RequestParam(required = false, value = "id") Long reservation_id) throws OurException {
        ModelMapper model_mapper = new ModelMapper();
        ArrayList<ReservationDto> reservations = (ArrayList<ReservationDto>) reservation_service.get_reservations(reservation_id).stream()
                .map(elementB -> model_mapper.map(elementB, ReservationDto.class))
                .collect(Collectors.toList());

        return reservations;
    }

    @PostMapping
    @PreAuthorize("@securityService.not_userAPI() && @securityService.premium_user()")
    public ReservationDto save_reservation(@RequestBody ReservationDto reservation) throws OurException {
        ModelMapper model_mapper = new ModelMapper();
        return model_mapper.map(reservation_service.save_reservation(model_mapper.map(reservation, ReservationModel.class)), ReservationDto.class);
    }

    @PostMapping(path = "/{reservation_id}/end")
    @PreAuthorize("@securityService.not_userAPI() && @securityService.premium_user()")
    public ReservationDto end_reservation(@PathVariable("reservation_id") Long reservation_id){
        ModelMapper model_mapper = new ModelMapper();
        ReservationDto reservation = null;

        try{
            reservation = model_mapper.map(reservation_service.end_reservation(model_mapper.map(reservation, ReservationModel.class)), ReservationDto.class);;
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Charger is not free");
        }

        return reservation;
    }
}
