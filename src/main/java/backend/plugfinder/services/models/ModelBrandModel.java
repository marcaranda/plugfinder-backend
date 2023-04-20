package backend.plugfinder.services.models;

import backend.plugfinder.helpers.ModelBrandId;
import backend.plugfinder.repositories.entity.ChargerTypeEntity;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

public class ModelBrandModel {
    //region Atributes
    private ModelBrandId id;
    private boolean known;
    private BrandModel brand_model;
    private UserModel user_model;

    private List<ChargerTypeModel> chargers_types;

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

    public BrandModel getBrand_model() {
        return brand_model;
    }

    public void setBrand_model(BrandModel brand_model) {
        this.brand_model = brand_model;
    }

    public UserModel getUser_model() {
        return user_model;
    }

    public void setUser_model(UserModel user_model) {
        this.user_model = user_model;
    }

    public List<ChargerTypeModel> getChargers_types() {
        return chargers_types;
    }

    public void setChargers_types(List<ChargerTypeModel> chargers_types) {
        this.chargers_types = chargers_types;
    }
    //endregion
}
