package backend.plugfinder.services.models;

import backend.plugfinder.helpers.CarId;
import jakarta.persistence.*;

public class CarModel {
    //region Atributes
    private CarId id;
    private String alias;
    private boolean deleted;
    private UserModel user_model;
    private ModelBrandModel model_brand_model;
    //endregion

    //region Getter & Setters
    public CarId getId() {
        return id;
    }

    public void setId(CarId id) {
        this.id = id;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public UserModel getUser_model() {
        return user_model;
    }

    public void setUser_model(UserModel user_model) {
        this.user_model = user_model;
    }

    public ModelBrandModel getModel_brand_model() {
        return model_brand_model;
    }

    public void setModel_brand_model(ModelBrandModel model_brand_model) {
        this.model_brand_model = model_brand_model;
    }

    //endregion
}
