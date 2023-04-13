package backend.plugfinder.repositories.entity;


import jakarta.persistence.*;

import java.sql.Date;
import java.sql.Time;

@Entity
@Table(name = "charge")
public class ChargeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id_charge;

    @ManyToOne
    @JoinColumn(name = "id_charger")
    private ChargerEntity charger;


    @ManyToOne
    @JoinColumn(name = "user_id")
    @JoinColumn(name = "licence")
    private CarEntity car;

    private Date date;

    private Time charged_time;

    private int charged_kw;

    private int co2;

    public ChargeEntity() {

    }

    public ChargeEntity(ChargerEntity charger, CarEntity car, Date date, Time charged_time, int charged_kw, int co2) {
        this.charger = charger;
        this.car = car;
        this.date = date;
        this.charged_time = charged_time;
        this.charged_kw = charged_kw;
        this.co2 = co2;
    }

    public ChargerEntity getCharger() {
        return charger;
    }

    public void setCharger(ChargerEntity charger) {
        this.charger = charger;
    }

    public CarEntity getCar() {
        return car;
    }

    public void setCar(CarEntity car) {
        this.car = car;
    }

    public int getId_charge() {
        return id_charge;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getCharged_time() {
        return charged_time;
    }

    public void setCharged_time(Time charged_time) {
        this.charged_time = charged_time;
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
