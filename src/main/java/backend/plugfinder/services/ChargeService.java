package backend.plugfinder.services;

import backend.plugfinder.services.models.ChargeModel;
import backend.plugfinder.repositories.ChargeRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
public class ChargeService {
    @Autowired
    ChargeRepo charge_repo;

    public ArrayList<ChargeModel> get_charges() {
        ModelMapper model_mapper = new ModelMapper();
        ArrayList<ChargeModel> charges = new ArrayList<>();
        charge_repo.findAll()

        return (ArrayList<ChargeModel>) charge_repo.findAll();
    }

    public ChargeModel save_charge(ChargeModel charge_model) {
        return charge_repo.save(chargeModel);
    }
}
