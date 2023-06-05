package backend.plugfinder.services;

import backend.plugfinder.helpers.OurException;
import backend.plugfinder.repositories.entity.ChargeEntity;
import backend.plugfinder.services.models.ChargeModel;
import backend.plugfinder.services.models.UserModel;
import backend.plugfinder.repositories.ChargeRepo;
import backend.plugfinder.services.models.ChargerModel;
import backend.plugfinder.services.models.ReservationModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.plugfinder.services.ReservationService;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;

@Service
public class ChargeService {
    @Autowired
    ChargeRepo charge_repo;

    @Autowired
    ChargerService charger_service;

    @Autowired
    UserService user_service;

    @Autowired
    ReservationService reservation_service;

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

        ChargerModel charger = charge_model.getCharger();
        charger_service.occupy(model_mapper.map(charger, ChargerModel.class));

        return model_mapper.map(charge_repo.save(model_mapper.map(charge_model, ChargeEntity.class)), ChargeModel.class);
    }

    public ChargeModel end_charge(ChargeModel charge_model) {

        ModelMapper model_mapper = new ModelMapper();

        ChargerModel charger = charge_model.getCharger();
        charger_service.disoccupy(model_mapper.map(charger, ChargerModel.class));

        charge_model.setEnded_at(Timestamp.from(Instant.now()));

        int potency = charger.getPotency();
        long duration = (charge_model.getEnded_at().getTime() - charge_model.getCreated_at().getTime()) / 1000 / 60 / 60;

        long points_charge = potency*duration;
        UserModel user = charge_model.getCar().getUser_model();
        user_service.add_points(user.getUser_id(), points_charge);

        return model_mapper.map(charge_repo.save(model_mapper.map(charge_model, ChargeEntity.class)), ChargeModel.class);
    }

    public ChargeModel get_charge(Long chargeId) {
        ModelMapper model_mapper = new ModelMapper();

        return model_mapper.map(charge_repo.findById(chargeId).get(), ChargeModel.class);
    }

    public ChargeModel save_charge_from_reservation(Long reservation_id) throws Exception {
        ModelMapper model_mapper = new ModelMapper();

        ReservationModel reservation = reservation_service.get_reservation_for_charge(reservation_id);

        ChargeModel charge = new ChargeModel();

        charge.setCar(reservation.getCar());
        charge.setCharger(reservation.getCharger());
        charge.setReservation(reservation);

        reservation_service.end_reservation(model_mapper.map(reservation, ReservationModel.class));

        return save_charge(charge);

    }

}
