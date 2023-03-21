package backend.plugfinder.helpers;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class ModelBrandId implements Serializable {
    //region Atributes
    @Column(name = "name")
    private String name;

    @Column(name = "brand_name")
    private String brand_name;

    @Column(name = "user_id")
    private long user_id;

    @Column(name = "autonomy")
    private String autonomy;
    //endregion

    //region Constructors
    public ModelBrandId() {
    }

    public ModelBrandId(String name, String brand_name) {
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

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getAutonomy() {
        return autonomy;
    }

    public void setAutonomy(String autonomy) {
        this.autonomy = autonomy;
    }

    //endregion
}
