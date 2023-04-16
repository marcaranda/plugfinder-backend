package backend.plugfinder.repositories.entity;

import backend.plugfinder.helpers.LocationId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "location")
public class LocationEntity {
    //region Atributes
    @EmbeddedId
    private LocationId id;
    //endregion

    //region Getters & Setters
    public LocationId getId() {
        return id;
    }

    public void setId(LocationId id) {
        this.id = id;
    }

    public double getLatitude() {
        return id.getLatitude();
    }

    public double getLongitude() {
        return id.getLongitude();
    }
    //endregion
}
