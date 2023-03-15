package backend.plugfinder.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class ModelBrandId implements Serializable {
    @Column(name = "name")
    private String name;

    @Column(name = "brand_name")
    private String brand_name;

    public ModelBrandId(){}

    public ModelBrandId(String name, String brand_name){
        this.name = name;
        this.brand_name = brand_name;
    }


    /*Getter & Setters*/
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
}
