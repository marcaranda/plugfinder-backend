package backend.plugfinder.repositories.entity;

import backend.plugfinder.helpers.ModelBrandId;
import jakarta.persistence.*;

@Entity
@Table(name = "model_brand")
public class ModelBrandEntity {
    //region Atributes
    @EmbeddedId
    private ModelBrandId id;
    @Column(nullable = false)
    private boolean known;
    @ManyToOne
    @MapsId("brand_name")
    @JoinColumn(name = "brand_name")
    private BrandEntity brand_model;
    @ManyToOne
    @MapsId("user_id")
    @JoinColumn(name = "user_id")
    private UserEntity user_model;

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

    public BrandEntity getBrand_model() {
        return brand_model;
    }

    public void setBrand_model(BrandEntity brand_model) {
        this.brand_model = brand_model;
    }

    public UserEntity getUser_model() {
        return user_model;
    }

    public void setUser_model(UserEntity user_model) {
        this.user_model = user_model;
    }

    /*public ChargerModel getChargerModel() {
        return chargerModel;
    }

    public void setChargerModel(ChargerModel chargerModel) {
        this.chargerModel = chargerModel;
    }*/
    //endregion
}
