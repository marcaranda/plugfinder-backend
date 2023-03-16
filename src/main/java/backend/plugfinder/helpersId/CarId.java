package backend.plugfinder.helpersId;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class CarId implements Serializable {
    @Column(name = "license")
    private String license;

    @Column(name = "user_id")
    private long id;

    public CarId() {}

    public CarId(String license, long id) {
        this.license = license;
        this.id = id;
    }


    /*Getter & Setters*/
    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
