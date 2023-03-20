package backend.plugfinder.helpersId;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class KnownModelBrandId implements Serializable {
    //region Atributes
    @Column(name = "name")
    private String name;

    @Column(name = "brand_name")
    private String brand_name;
    //endregion

    //region Constructors
    public KnownModelBrandId() {
    }

    public KnownModelBrandId(String name, String brand_name) {
        this.name = name;
        this.brand_name = brand_name;

    }
    //endregion

    //region Getter & Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }
    //endregion
}
