package backend.plugfinder.controllers.dto;

import backend.plugfinder.helpers.ModelBrandId;
import jakarta.persistence.*;

public class ModelBrandDto {
    //region Atributes
    private ModelBrandId id;
    private boolean known;
    private BrandDto brand_model;
    private UserDto user_model;

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

    public BrandDto getBrand_model() {
        return brand_model;
    }

    public void setBrand_model(BrandDto brand_model) {
        this.brand_model = brand_model;
    }

    public UserDto getUser_model() {
        return user_model;
    }

    public void setUser_model(UserDto user_model) {
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
