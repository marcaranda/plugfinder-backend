package backend.plugfinder.services.models;


import jakarta.persistence.*;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;

public class ReservationModel {

    private long id_reservation;

    private ChargerModel charger;

    private CarModel car;

    private Timestamp created_at;

    private Timestamp ended_at;


    public long get_id_reservation() {
        return id_reservation;
    }

    public void set_id_reservation(long id_reservation) {
        this.id_reservation = id_reservation;
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

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getEnded_at() {
        return ended_at;
    }

    public void setEnded_at(Timestamp ended_at) {
        this.ended_at = ended_at;
    }

    public boolean timeExtended(){

        Timestamp now = new Timestamp(System.currentTimeMillis());

        long milliseconds = now.getTime() - this.created_at.getTime();
        long minutes = milliseconds / (60 * 1000);

        return minutes > 3;

    }
}
