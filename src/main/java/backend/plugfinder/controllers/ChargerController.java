package backend.plugfinder.controllers;

import backend.plugfinder.controllers.dto.ChargerDto;
import backend.plugfinder.services.ChargerService;
import backend.plugfinder.services.models.ChargerModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/chargers")
public class ChargerController {
    @Autowired
    ChargerService charger_service;

    @GetMapping
    public ArrayList<ChargerDto> get_chargers(){
        ModelMapper model_mapper = new ModelMapper();
        ArrayList<ChargerDto> chargers = (ArrayList<ChargerDto>) charger_service.get_chargers().stream()
                .map(elementB -> model_mapper.map(elementB, ChargerDto.class))
                .collect(Collectors.toList());

        return chargers;
    }


    //region Post Methods
    @PostMapping("/new")
    public ChargerDto save_charger(@RequestBody ChargerDto chargerModel){
        ModelMapper model_mapper = new ModelMapper();

        return model_mapper.map(charger_service.save_charger(model_mapper.map(chargerModel, ChargerModel.class)), ChargerDto.class);
    }
    //endregion

    //region Delete Methods
    @DeleteMapping(path = "/{id}")
    public String delete_charger(@PathVariable("id") int id){
        if (charger_service.delete_charger(id)){
            return "Se elimino correctamente el cargador con id " + id;
        }
        else{
            return "No se ha podido eliminar el cargador con id " + id;
        }
    }
}
