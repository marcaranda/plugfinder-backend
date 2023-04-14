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
import java.util.ArrayList;
import java.sql.Date;
import java.util.Map;

@Service
public class ChargeService {
    @Autowired
    ChargeRepo charge_repo;

    public ArrayList<ChargeModel> get_charges(Long user_id) throws OurException {
        ModelMapper model_mapper = new ModelMapper();
        ArrayList<ChargeModel> charges = new ArrayList<>();

        if (user_id != null){
            if (new TokenValidator().validate_id_with_token(user_id)) {
                charge_repo.get_charges_by_user_id(user_id).forEach(elementB -> charges.add(model_mapper.map(elementB, ChargeModel.class)));
                return charges;
            }
            else {
                throw new OurException("El user_id enviado es diferente al especificado en el token");
            }
        }
        else charge_repo.findAll().forEach(elementB -> charges.add(model_mapper.map(elementB, ChargeModel.class)));
        return charges;
    }

    public ChargeModel save_charge(ChargeModel charge_model) {
        ModelMapper model_mapper = new ModelMapper();
        charge_model.setDate(new Date(123,4,31));
        charge_model.setCharge_time(new Time(16,34,40));
        return model_mapper.map(charge_repo.save(model_mapper.map(charge_model, ChargeEntity.class)), ChargeModel.class);
    }
}
