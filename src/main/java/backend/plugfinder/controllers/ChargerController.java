package backend.plugfinder.controllers;

import backend.plugfinder.models.CarModel;
import backend.plugfinder.models.ChargerModel;
import backend.plugfinder.services.ChargerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
@RestController
@RequestMapping("/chargers")
public class ChargerController {
    @Autowired
    ChargerService chargerService;

    @GetMapping
    public ArrayList<ChargerModel> getChargers(){
        return chargerService.getChargers();
    }


    //region Post Methods
    @PostMapping
    public ChargerModel saveCharger(@RequestBody ChargerModel chargerModel){
        return chargerService.saveCharger(chargerModel);
    }
    //endregion

    //region Delete Methods
    @DeleteMapping(path = "/{id}")
    public String deleteCharger(@PathVariable("id") String id){
        if (chargerService.deleteCharger(id)){
            return "Se elimino correctamente el cargador con id " + id;
        }
        else{
            return "No se ha podido eliminar el cargador con id " + id;
        }
    }
}
