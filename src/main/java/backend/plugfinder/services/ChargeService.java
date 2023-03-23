package backend.plugfinder.services;

import backend.plugfinder.models.ChargeModel;
import backend.plugfinder.models.ChargerModel;
import backend.plugfinder.repositories.ChargeRepo;

import java.util.ArrayList;

public class ChargeService {
    ChargeRepo chargeRepo;

    public ArrayList<ChargeModel> getCharges() {
        return (ArrayList<ChargeModel>) chargeRepo.findAll();
    }

    public ChargeModel saveCharge(ChargeModel chargeModel) {
        return chargeRepo.save(chargeModel);
    }
}
