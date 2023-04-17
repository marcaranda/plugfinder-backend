package backend.plugfinder.repositories.entity;

import jakarta.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import java.sql.Timestamp;

@Entity
@Table(name = "charge")
public class ChargeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private long id_charge;

    @ManyToOne
    @JoinColumn(name = "id_charger")
    private ChargerEntity charger;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JoinColumn(name = "license")
    private CarEntity car;


    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Timestamp created_at;

    @Column(name = "ended_at", nullable = true)
    private Timestamp ended_at;

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

    public Timestamp getEnded_at() {
        return ended_at;
    }

    public void setEnded_at(Timestamp ended_at) {
        this.ended_at = ended_at;
    }
}
