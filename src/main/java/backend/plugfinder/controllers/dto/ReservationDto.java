package backend.plugfinder.controllers.dto;


import backend.plugfinder.services.models.CarModel;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

public class ReservationDto {

    private long id_reservation;

    private ChargerDto charger;

    private CarDto car;

    private Timestamp created_at;

    private Timestamp ended_at;


    public long get_id_reservation() {
        return id_reservation;
    }

    public void set_id_reservation(long id_reservation) {
        this.id_reservation = id_reservation;
    }

    public ChargerDto getCharger() {
        return charger;
    }

    public void setCharger(ChargerDto charger) {
        this.charger = charger;
    }

    public CarDto getCar() {
        return car;
    }

    public void setCar(CarDto car) {
        this.car = car;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getEnded_at() {
        return ended_at;
    }

    public void setEnded_at(Timestamp ended_at) {
        this.ended_at = ended_at;
    }
}
