package backend.plugfinder.helpers;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class LocationId implements Serializable {
    //region Atributes
    @Column(name = "latitude")
    private String latitude;

    @Column(name = "longitude")
    private String longitude;
    //endregion

    //region Constructors
    public LocationId() {
    }

    public LocationId(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
    //endregion

    //Region Getters & Setters
    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
    //endregion
}
