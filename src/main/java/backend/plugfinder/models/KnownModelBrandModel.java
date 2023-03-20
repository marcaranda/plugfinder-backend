package backend.plugfinder.models;

import backend.plugfinder.helpersId.KnownModelBrandId;
import jakarta.persistence.*;

@Entity
@Table(name = "known_model_brand")
public class KnownModelBrandModel {
    //region Atributes
    @EmbeddedId
    private KnownModelBrandId id;
    @Column(nullable = false)
    private boolean known;
    @Column(nullable = false)
    private String autonomy;
    @ManyToOne
    @MapsId("brand_name")
    @JoinColumn(name = "brand_name")
    private BrandModel brand_model;
    /*@ManyToOne
    @JoinColumn(name = "charger_id")
    private ChargerModel chargerModel;*/
    //endregion

    //region Getter & Setters
    public KnownModelBrandId getId() {
        return id;
    }

    public void setId(KnownModelBrandId id) {
        this.id = id;
    }

    public boolean isKnown() {
        return known;
    }

    public void setKnown(boolean known) {
        this.known = known;
    }

    public String getAutonomy() {
        return autonomy;
    }

    public void setAutonomy(String autonomy) {
        this.autonomy = autonomy;
    }

    public BrandModel getBrand_model() {
        return brand_model;
    }

    public void setBrand_model(BrandModel brand_model) {
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
