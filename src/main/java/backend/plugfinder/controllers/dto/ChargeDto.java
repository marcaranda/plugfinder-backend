package backend.plugfinder.controllers.dto;


import backend.plugfinder.services.models.CarModel;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

public class ChargeDto {

    private long id_charge;

    private ChargerDto charger;

    private CarDto car;

    private Timestamp created_at;

    private int charged_kw;

    private int co2;


    public long getId_charge() {
        return id_charge;
    }

    public void setId_charge(long id_charge) {
        this.id_charge = id_charge;
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
}
