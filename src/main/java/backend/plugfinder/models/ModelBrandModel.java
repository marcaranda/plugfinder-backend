package backend.plugfinder.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "ModelBrand")
public class ModelBrandModel {
    /*@EmbeddedId
    @Column(unique = true, nullable = false)
    private String name;*/
    @EmbeddedId
    private ModelBrandId id;
    @Column(nullable = false)
    private boolean known;
    @ManyToOne
    @MapsId("brand_name")
    @JoinColumn(name = "brand_name")
    private BrandModel brandModel;
    /*@ManyToOne
    private ChargerModel chargerModel;*/
    /*@OneToMany
    private List<CarModel> cars;*/


    /*Getter & Setters*/

    /*public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }*/

    public ModelBrandId getId() {
        return id;
    }

    public void setId(ModelBrandId id) {
        this.id = id;
    }

    public boolean isKnown() {
        return known;
    }

    public void setKnown(boolean known) {
        this.known = known;
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

    /*public List<CarModel> getCars() {
        return cars;
    }

    public void setCars(List<CarModel> cars) {
        this.cars = cars;
    }*/
}
