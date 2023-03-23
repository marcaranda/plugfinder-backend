package backend.plugfinder.models;

import jakarta.persistence.*;
import java.sql.Time;

@Entity
@Table(name = "charger")
public class ChargerModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id_charger;

    private String alias_charger;

    private String state;

    private int price_x_kw;

    private Time max_time_charging;

    // Constructores, getters y setters

    public ChargerModel() {}

    public ChargerModel(int id_charger, String alias_charger, String state, int price_x_kw, Time max_time_charging) {
        this.id_charger = id_charger;
        this.alias_charger = alias_charger;
        this.state = state;
        this.price_x_kw = price_x_kw;
        this.max_time_charging = max_time_charging;
    }

    public int getId_charger() {
        return id_charger;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAlias_charger() {
        return alias_charger;
    }

    public void setAlias_charger(String alias_charger) {
        this.alias_charger = alias_charger;
    }

    public int getPrice_x_kw() {
        return price_x_kw;
    }

    public void setPrice_x_kw(int price_x_kw) {
        this.price_x_kw = price_x_kw;
    }

    public Time getMax_time_charging() {
        return max_time_charging;
    }

    public void setMax_time_charging(Time max_time_charging) {
        this.max_time_charging = max_time_charging;
    }
}
