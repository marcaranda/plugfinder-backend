package backend.plugfinder.controllers;

import backend.plugfinder.models.ChargeModel;
import backend.plugfinder.models.ChargerModel;
import backend.plugfinder.services.ChargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
@RestController
@RequestMapping("/charges")
public class ChargeController {
    @Autowired
    ChargeService chargeService;

    //region Get Methods


    @GetMapping
    public ArrayList<ChargeModel> getCharger(){
        return chargeService.getCharges();
    }

    @PostMapping
    public ChargeModel saveCharge(@RequestBody ChargeModel chargeModel){
        return chargeService.saveCharge(chargeModel);
    }


}
