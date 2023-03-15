package backend.plugfinder.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Brand")
public class ModelBrandModel {
    @Id
    @Column(unique = true, nullable = false)
    private String name;
    @Column(nullable = false)
    private boolean knowed;
    @ManyToOne
    private BrandModel brandModel;
    /*@ManyToOne
    private ChargerModel chargerModel;*/
    @OneToMany
    private List<CarModel> cars;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isKnowed() {
        return knowed;
    }

    public void setKnowed(boolean knowed) {
        this.knowed = knowed;
    }

    public BrandModel getBrandModel() {
        return brandModel;
    }

    public void setBrandModel(BrandModel brandModel) {
        this.brandModel = brandModel;
    }

    /*public ChargerModel getChargerModel() {
        return chargerModel;
    }

    public void setChargerModel(ChargerModel chargerModel) {
        this.chargerModel = chargerModel;
    }*/

    public List<CarModel> getCars() {
        return cars;
    }

    public void setCars(List<CarModel> cars) {
        this.cars = cars;
    }
}
