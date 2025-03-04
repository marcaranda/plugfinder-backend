package backend.plugfinder.controllers;

import backend.plugfinder.controllers.dto.ChargerDto;
import backend.plugfinder.helpers.OurException;
import backend.plugfinder.services.ChargerService;
import backend.plugfinder.services.UserService;
import backend.plugfinder.services.models.ChargerModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/chargers")
public class ChargerController {
    @Autowired
    ChargerService charger_service;
    @Autowired
    UserService user_service;

    //region Get Methods
    @GetMapping
    public ArrayList<ChargerDto> get_chargers(@RequestParam(required = false, value = "public") Boolean is_public,
                                              @RequestParam(required = false, value = "latitude") Double latitude,
                                              @RequestParam(required = false, value = "longitude") Double longitude ,
                                              @RequestParam(required = false, value="types") Integer[] types,
                                              @RequestParam (required = false, value = "speed_lower") Long speed_lower,
                                              @RequestParam (required = false, value = "speed_upper") Long speed_upper,
                                              @RequestParam (required = false, value = "price") Long price,
                                              @RequestParam (required = false, value = "radius") Long radius){

        ModelMapper model_mapper = new ModelMapper();
        ArrayList<ChargerDto> chargers = (ArrayList<ChargerDto>) charger_service.buscar_cargadores(is_public, latitude, longitude, types, speed_lower, speed_upper , radius).stream()
                .map(elementB -> model_mapper.map(elementB, ChargerDto.class))
                .collect(Collectors.toList());

        return chargers;
    }

    @GetMapping("/{charger_id}")
    @PreAuthorize("@securityService.not_userAPI()")
    public ChargerDto get_charger(@PathVariable("charger_id") Long charger_id){
        ModelMapper model_mapper = new ModelMapper();
        return model_mapper.map(charger_service.find_charger_by_id(charger_id), ChargerDto.class);
    }

    @GetMapping("/user/{user_id}")
    @PreAuthorize("@securityService.not_userAPI()")
    public ArrayList<ChargerDto> get_user_chargers(@PathVariable("user_id") Long user_id) throws OurException {
        ModelMapper model_mapper = new ModelMapper();
        ArrayList<ChargerDto> chargers = (ArrayList<ChargerDto>) charger_service.get_user_chargers(user_id).stream()
                .map(elementB -> model_mapper.map(elementB, ChargerDto.class))
                .collect(Collectors.toList());

        return chargers;
    }
    //endregion

    //region Post Methods
    @PostMapping
    @PreAuthorize("@securityService.not_userAPI()")
    public ChargerDto save_charger(@RequestBody ChargerDto chargerModel){
        ModelMapper model_mapper = new ModelMapper();

        return model_mapper.map(charger_service.save_charger(model_mapper.map(chargerModel, ChargerModel.class)), ChargerDto.class);
    }
    //endregion

    //region Delete Methods
    @DeleteMapping(path = "/{charger_id}")
    @PreAuthorize("@securityService.not_userAPI()")
    public String delete_charger(@PathVariable("charger_id") Long charger_id) throws OurException {
        return charger_service.delete_charger(charger_id);
    }
    //endregion

    //region Put Methods
    @PutMapping("/{charger_id}/edit")
    @PreAuthorize("@securityService.not_userAPI()")
    public ChargerDto update_charger(@PathVariable("charger_id") Long charger_id, @RequestParam(required = false, value = "price") Double price,
                                     @RequestParam(required = false, value = "current") String electric_current, @RequestParam(required = false, value = "potency") Integer potency,
                                     @RequestParam(required = false, value = "types") ArrayList<Long> chargers_type_id, @RequestBody(required = false) String photo) throws OurException {
        ModelMapper model_mapper = new ModelMapper();

        return model_mapper.map(charger_service.update_charger(charger_id, price, electric_current, potency, chargers_type_id, photo), ChargerDto.class);
    }

    @PutMapping("/{charger_id}/active")
    @PreAuthorize("@securityService.not_userAPI()")
    public ChargerDto active_charger(@PathVariable("charger_id") Long id) throws OurException {
        ModelMapper model_mapper = new ModelMapper();

        return model_mapper.map(charger_service.active_charger(id), ChargerDto.class);
    }
    //endregion
}
