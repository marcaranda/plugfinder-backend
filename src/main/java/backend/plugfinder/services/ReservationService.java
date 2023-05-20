package backend.plugfinder.services;

import backend.plugfinder.helpers.OurException;
import backend.plugfinder.repositories.ReservationRepo;
import backend.plugfinder.repositories.entity.ReservationEntity;

import backend.plugfinder.services.models.ChargerModel;
import backend.plugfinder.services.models.ReservationModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;

@Service
public class ReservationService {
    @Autowired
    ReservationRepo reservation_repo;

    @Autowired
    ChargerService charger_sercice;

    public ArrayList<ReservationModel> get_reservations(Long reservation_id) throws OurException {
        ModelMapper model_mapper = new ModelMapper();
        ArrayList<ReservationModel> reservations = new ArrayList<>();

        if (reservation_id != null){
            reservations.add(get_reservation(reservation_id));
        }
        else reservation_repo.findAll().forEach(elementB -> reservations.add(model_mapper.map(elementB, ReservationModel.class)));

        return reservations;
    }

    public ReservationModel save_reservation(ReservationModel reservation_model) {
        ModelMapper model_mapper = new ModelMapper();

        ChargerModel charger = reservation_model.getCharger();
        charger_sercice.occupy(charger);

        return model_mapper.map(reservation_repo.save(model_mapper.map(reservation_model, ReservationEntity.class)), ReservationModel.class);
    }

    public ReservationModel end_reservation(ReservationModel reservation_model) {
        ModelMapper model_mapper = new ModelMapper();

        ChargerModel charger = reservation_model.getCharger();
        charger_sercice.disoccupy(charger);

        reservation_model.setEnded_at(Timestamp.from(Instant.now()));
        return model_mapper.map(reservation_repo.save(model_mapper.map(reservation_model, ReservationEntity.class)), ReservationModel.class);
    }

    public ReservationModel get_reservation(Long reservation_id) {
        ModelMapper model_mapper = new ModelMapper();

        return model_mapper.map(reservation_repo.findById(reservation_id).get(), ReservationModel.class);
    }

    public ReservationModel get_reservation_for_charge(Long reservation_id) throws Exception {

        ReservationModel reservation = get_reservation(reservation_id);

        if (reservation.getEnded_at() == null) {

            return reservation;

        }else{
            throw new Exception("Reservation already ended");
        }

    }

}
