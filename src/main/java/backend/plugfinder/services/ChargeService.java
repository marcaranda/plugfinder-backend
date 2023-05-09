package backend.plugfinder.services;

import backend.plugfinder.helpers.OurException;
import backend.plugfinder.helpers.TokenValidator;
import backend.plugfinder.repositories.entity.ChargeEntity;
import backend.plugfinder.services.models.CarModel;
import backend.plugfinder.services.models.ChargeModel;
import backend.plugfinder.repositories.ChargeRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.sql.Date;
import java.util.Map;

@Service
public class ChargeService {
    @Autowired
    ChargeRepo charge_repo;

    public ArrayList<ChargeModel> get_charges(Long charge_id) throws OurException {
        ModelMapper model_mapper = new ModelMapper();
        ArrayList<ChargeModel> charges = new ArrayList<>();

        if (charge_id != null){
            charges.add(get_charge(charge_id));
        }
        else charge_repo.findAll().forEach(elementB -> charges.add(model_mapper.map(elementB, ChargeModel.class)));
        return charges;
    }

    public ChargeModel save_charge(ChargeModel charge_model) {
        ModelMapper model_mapper = new ModelMapper();
        return model_mapper.map(charge_repo.save(model_mapper.map(charge_model, ChargeEntity.class)), ChargeModel.class);
    }

    public ChargeModel end_charge(ChargeModel charge_model) {
        ModelMapper model_mapper = new ModelMapper();

        charge_model.setEnded_at(Timestamp.from(Instant.now()));
        return model_mapper.map(charge_repo.save(model_mapper.map(charge_model, ChargeEntity.class)), ChargeModel.class);
    }

    public ChargeModel get_charge(Long chargeId) {
        ModelMapper model_mapper = new ModelMapper();

        return model_mapper.map(charge_repo.findById(chargeId).get(), ChargeModel.class);
    }
}
