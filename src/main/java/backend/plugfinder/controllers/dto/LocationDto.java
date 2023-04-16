package backend.plugfinder.controllers.dto;

import backend.plugfinder.helpers.LocationId;

public class LocationDto {
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
