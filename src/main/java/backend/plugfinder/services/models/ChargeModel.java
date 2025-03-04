package backend.plugfinder.services.models;


import backend.plugfinder.controllers.dto.ReservationDto;
import jakarta.persistence.*;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

public class ChargeModel {

    private long id_charge;

    private ChargerModel charger;

    private CarModel car;

    private Timestamp created_at;

    private Timestamp ended_at;

    private int charged_kw;

    private int co2;

    private ReservationModel reservation;


    public long getId_charge() {
        return id_charge;
    }

    public void setId_charge(long id_charge) {
        this.id_charge = id_charge;
    }

    public ChargerModel getCharger() {
        return charger;
    }

    public void setCharger(ChargerModel charger) {
        this.charger = charger;
    }

    public CarModel getCar() {
        return car;
    }

    public void setCar(CarModel car) {
        this.car = car;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public int getCharged_kw() {
        return charged_kw;
    }

    public void setCharged_kw(int charged_kw) {
        this.charged_kw = charged_kw;
    }

    public int getCo2() {
        return co2;
    }

    public void setCo2(int co2) {
        this.co2 = co2;
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

    public ReservationModel getReservation() {
        return reservation;
    }

    public void setReservation(ReservationModel reservation) {
        this.reservation = reservation;
    }
}
