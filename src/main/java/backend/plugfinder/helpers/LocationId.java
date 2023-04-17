package backend.plugfinder.helpers;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class LocationId implements Serializable {
    //region Atributes
    @Column(name = "latitude")
    private double latitude;

    @Column(name = "longitude")
    private double longitude;
    //endregion

    //region Constructors
    public LocationId() {
    }

    public LocationId(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
    //endregion

    //Region Getters & Setters
    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    //endregion
}
