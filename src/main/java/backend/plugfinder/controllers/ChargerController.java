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

    @GetMapping
    public ArrayList<ChargerDto> get_chargers(@RequestParam(required = false, value = "public") Boolean is_public, @RequestParam(required = false, value = "latitude") Double latitude, @RequestParam(required = false, value = "longitude") Double longitude , @RequestParam (required = false, value = "type") Integer type, @RequestParam (required = false, value = "real_charge_speed") Long real_charge_speed, @RequestParam (required = false, value = "price") Long price, @RequestParam (required = false, value = "radius") Long radius){
        ModelMapper model_mapper = new ModelMapper();
        ArrayList<ChargerDto> chargers = (ArrayList<ChargerDto>) charger_service.buscar_cargadores(is_public, latitude, longitude, type, real_charge_speed , radius).stream()
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

    /**
     * This method returns all the chargers that are public
     * @return ArrayList<ChargerDto> - List of public chargers
     * /
    @GetMapping("/location")
    public ArrayList<ChargerDto> get_chargers_by_location(@RequestParam("latitude") double latitude, @RequestParam("longitude") double longitude){
        ModelMapper model_mapper = new ModelMapper();
        ArrayList<ChargerDto> chargers = (ArrayList<ChargerDto>) charger_service.get_chargers_by_location(latitude, longitude).stream()
                .map(elementB -> model_mapper.map(elementB, ChargerDto.class))
                .collect(Collectors.toList());

        return chargers;
    }*/


    //region Post Methods
    @PostMapping
    @PreAuthorize("@securityService.not_userAPI()")
    public ChargerDto save_charger(@RequestBody ChargerDto chargerModel){
        ModelMapper model_mapper = new ModelMapper();

        return model_mapper.map(charger_service.save_charger(model_mapper.map(chargerModel, ChargerModel.class)), ChargerDto.class);
    }
    //endregion

    //region Delete Methods
    @DeleteMapping(path = "/{id}")
    @PreAuthorize("@securityService.not_userAPI()")
    public String delete_charger(@PathVariable("id") int id){
        if (charger_service.delete_charger(id)){
            return "Se elimino correctamente el cargador con id " + id;
        }
        else{
            return "No se ha podido eliminar el cargador con id " + id;
        }
    }

    @PutMapping("/update")
    @PreAuthorize("@securityService.not_userAPI()")
    public ChargerDto update_charger(@RequestBody ChargerDto charger_dto) throws OurException {
        ModelMapper model_mapper = new ModelMapper();

        return model_mapper.map(charger_service.update_charger(model_mapper.map(charger_dto, ChargerModel.class)), ChargerDto.class);
    }
}
