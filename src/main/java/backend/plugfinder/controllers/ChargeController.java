package backend.plugfinder.controllers;

import backend.plugfinder.helpers.TokenValidator;
import backend.plugfinder.models.ChargeModel;
import backend.plugfinder.models.OurException;
import backend.plugfinder.services.ChargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Objects;

@RestController
@RequestMapping("/charges")
public class ChargeController {
    @Autowired
    ChargeService chargeService;

    //region Get Methods


    @GetMapping
    public ArrayList<ChargeModel> getCharge(@RequestParam(required = false, value = "id") long user_id) throws OurException {
        if (Objects.isNull(user_id)) {
            return chargeService.getCharges();
        }
        else {
            if (new TokenValidator().validate_id_with_token(user_id)) {
                return chargeService.get_charges_by_user_id(user_id);
            }
            else {
                throw new OurException("El user_id enviado es diferente al especificado en el token");
            }
        }
    }

    @PostMapping(path = "/new")
    public ChargeModel saveCharge(@RequestBody ChargeModel chargeModel){
        return chargeService.saveCharge(chargeModel);
    }
}
