package backend.plugfinder.controllers;

import backend.plugfinder.controllers.dto.ChargerDto;
import backend.plugfinder.services.ChargerService;
import backend.plugfinder.services.UserService;
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
    @Autowired
    UserService user_service;

    @GetMapping
    public ArrayList<ChargerDto> get_chargers(){
        ModelMapper model_mapper = new ModelMapper();
        ArrayList<ChargerDto> chargers = (ArrayList<ChargerDto>) charger_service.get_chargers().stream()
                .map(elementB -> model_mapper.map(elementB, ChargerDto.class))
                .collect(Collectors.toList());

        return chargers;
    }

    /**
     * This method returns all the chargers that are public
     * @return ArrayList<ChargerDto> - List of public chargers
     */
    @GetMapping("/private")
    public ArrayList<ChargerDto> get_private_chargers(){
        ModelMapper model_mapper = new ModelMapper();
        ArrayList<ChargerDto> chargers = (ArrayList<ChargerDto>) charger_service.get_private_chargers().stream()
                .map(elementB -> model_mapper.map(elementB, ChargerDto.class))
                .collect(Collectors.toList());

        return chargers;
    }

    /**
     * This method returns all the chargers that are public
     * @return ArrayList<ChargerDto> - List of public chargers
     */
    @GetMapping("/location")
    public ArrayList<ChargerDto> get_chargers_by_location(@RequestParam("latitude") double latitude, @RequestParam("longitude") double longitude){
        ModelMapper model_mapper = new ModelMapper();
        ArrayList<ChargerDto> chargers = (ArrayList<ChargerDto>) charger_service.get_chargers_by_location(latitude, longitude).stream()
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

    /**
     * This method saves a private charger in the DB
     * @param user_id - Id of the user that is creating the charger
     * @param chargerModel - Charger to be saved
     * @return ChargerDto - Saved charger
     */
    @PostMapping("/{user_id}/private/new")
    public ChargerDto save_private_charger(@PathVariable("user_id") Long user_id, @RequestBody ChargerDto chargerModel){
        ModelMapper model_mapper = new ModelMapper();

        return model_mapper.map(charger_service.save_private_charger(user_service.find_user_by_id(user_id), model_mapper.map(chargerModel, ChargerModel.class)), ChargerDto.class);
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
