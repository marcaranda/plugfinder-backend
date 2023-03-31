package backend.plugfinder.models;


import jakarta.persistence.*;

import java.sql.Date;
import java.sql.Time;

@Entity
@Table(name = "charge")
public class ChargeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private long id_charge;

    @ManyToOne
    @JoinColumn(name = "id_charger")
    private ChargerModel charger;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JoinColumn(name = "license")
    private CarModel car;

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private Time charge_time;

    @Column(nullable = false)
    private int charged_kw;

    @Column(nullable = false)
    private int co2;


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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getCharge_time() {
        return charge_time;
    }

    public void setCharge_time(Time charge_time) {
        this.charge_time = charge_time;
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
