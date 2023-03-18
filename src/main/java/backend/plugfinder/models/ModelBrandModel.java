package backend.plugfinder.models;

import backend.plugfinder.helpersId.ModelBrandId;
import jakarta.persistence.*;

@Entity
@Table(name = "ModelBrand")
public class ModelBrandModel {
    //region Atributes
    @EmbeddedId
    private ModelBrandId id;
    @Column(nullable = false)
    private boolean known;
    @ManyToOne
    @MapsId("brand_name")
    @JoinColumn(name = "brand_name")
    private BrandModel brand_model;
    /*@ManyToOne
    @JoinColumn(name = "charger_id")
    private ChargerModel chargerModel;*/
    //endregion

    //region Getter & Setters
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
        return brand_model;
    }

    public void setBrandModel(BrandModel brand_model) {
        this.brand_model = brand_model;
    }

    /*public ChargerModel getChargerModel() {
        return chargerModel;
    }

    public void setChargerModel(ChargerModel chargerModel) {
        this.chargerModel = chargerModel;
    }*/
    //endregion
}
