package backend.plugfinder.repositories.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "reservation")
public class ReservationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private long id_reservation;

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


    public long get_id_reservation() {
        return id_reservation;
    }

    public void set_id_reservation(long id_reservation) {
        this.id_reservation = id_reservation;
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

    public Timestamp getEnded_at() {
        return ended_at;
    }

    public void setEnded_at(Timestamp ended_at) {
        this.ended_at = ended_at;
    }
}


