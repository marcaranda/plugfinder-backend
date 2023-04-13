package backend.plugfinder.controllers.dto;

import java.sql.Time;

public class ChargerDto {

    private long id_charger;

    private String alias_charger;

    private String state;

    private int price_x_kw;

    private Time max_time_charging;

    //region Constructores, getters y setters

    public long getId_charger() {
        return id_charger;
    }

    public void setId_charger(long id_charger) {
        this.id_charger = id_charger;
    }

    public String getAlias_charger() {
        return alias_charger;
    }

    public void setAlias_charger(String alias_charger) {
        this.alias_charger = alias_charger;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

    //endregion
}
