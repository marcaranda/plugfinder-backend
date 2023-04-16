package backend.plugfinder.controllers.dto;

import jakarta.persistence.*;

public class ChargerTypeDto {
    //region Atributes
    private long type_id;

    private String name;

    private String electric_current;
    //endregion

    //region Getters & Setters
    public long getType_id() {
        return type_id;
    }

    public void setType_id(long type_id) {
        this.type_id = type_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getElectric_current() {
        return electric_current;
    }

    public void setElectric_current(String electric_current) {
        this.electric_current = electric_current;
    }
    //endregion
}
