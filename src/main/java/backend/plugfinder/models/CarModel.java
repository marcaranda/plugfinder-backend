package backend.plugfinder.models;

import jakarta.persistence.*;

@Entity
@Table(name = "Car")
public class CarModel {
    @Id
    @Column(unique = true, nullable = false)
    private String license;
    /*@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id_car;*/
    @Column(nullable = false)
    private String alias;
    @Column(nullable = false)
    private String model_brand;
    @Column(nullable = false)
    private String autonomy;

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getModel_brand() {
        return model_brand;
    }

    public void setModel_brand(String model_brand) {
        this.model_brand = model_brand;
    }

    public String getAutonomy() {
        return autonomy;
    }

    public void setAutonomy(String autonomy) {
        this.autonomy = autonomy;
    }
}
