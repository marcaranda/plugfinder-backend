package backend.plugfinder.controllers;

import backend.plugfinder.controllers.dto.ChargerTypeDto;
import backend.plugfinder.services.ChargerTypeService;
import backend.plugfinder.services.models.ChargerTypeModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/chargertype")
public class ChargerTypeController {
    @Autowired
    ChargerTypeService charger_type_service;

    //region Get Methods
    @GetMapping
    @PreAuthorize("@securityService.not_userAPI()")
    public ArrayList<ChargerTypeDto> get_chargers_types(){
        ModelMapper model_mapper = new ModelMapper();
        ArrayList<ChargerTypeDto> chargers_types = (ArrayList<ChargerTypeDto>) charger_type_service.get_chargers_types().stream()
                .map(elementB -> model_mapper.map(elementB, ChargerTypeDto.class))
                .collect(Collectors.toList());
        return chargers_types;
    }

    @GetMapping(path = "id/{id}")
    @PreAuthorize("@securityService.not_userAPI()")
    public ChargerTypeDto get_charger_type(@PathVariable("id") long id){
        ModelMapper model_mapper = new ModelMapper();

        return model_mapper.map(charger_type_service.get_charger_type_by_id(id), ChargerTypeDto.class);
    }
    //endregion

    //region Post Methods
    @PostMapping
    @PreAuthorize("@securityService.not_userAPI()")
    public ChargerTypeDto save_charger_type(@RequestBody ChargerTypeDto charger_type_dto){
        ModelMapper model_mapper = new ModelMapper();

        return model_mapper.map(charger_type_service.save_charger_type(model_mapper.map(charger_type_dto, ChargerTypeModel.class)), ChargerTypeDto.class);
    }
    //endregion
}
