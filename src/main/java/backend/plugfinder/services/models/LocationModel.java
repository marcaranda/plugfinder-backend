package backend.plugfinder.services.models;

import backend.plugfinder.helpers.LocationId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

public class LocationModel {
    //region Atributes
    private LocationId id;
    //endregion

    //region Getters & Setters
    public LocationId getId() {
        return id;
    }

    public void setId(LocationId id) {
        this.id = id;
    }
    //endregion
}
