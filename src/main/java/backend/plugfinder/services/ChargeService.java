package backend.plugfinder.services;

import backend.plugfinder.models.ChargeModel;
import backend.plugfinder.models.ChargerModel;
import backend.plugfinder.repositories.ChargeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ChargeService {
    @Autowired
    ChargeRepo chargeRepo;

    public ArrayList<ChargeModel> getCharges() {
        return (ArrayList<ChargeModel>) chargeRepo.findAll();
    }

    public ChargeModel saveCharge(ChargeModel chargeModel) {
        return chargeRepo.save(chargeModel);
    }
}
