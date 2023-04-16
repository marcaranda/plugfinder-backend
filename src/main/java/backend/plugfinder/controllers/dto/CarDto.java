package backend.plugfinder.controllers.dto;

import backend.plugfinder.helpers.CarId;
import jakarta.persistence.*;

public class CarDto {
    //region Atributes
    private CarId id;
    private boolean deleted;
    private UserDto user_model;
    private ModelBrandDto model_brand_model;
    //endregion

    //region Getter & Setters
    public CarId getId() {
        return id;
    }

    public void setId(CarId id) {
        this.id = id;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public UserDto getUser_model() {
        return user_model;
    }

    public void setUser_model(UserDto user_model) {
        this.user_model = user_model;
    }

    public ModelBrandDto getModel_brand_model() {
        return model_brand_model;
    }

    public void setModel_brand_model(ModelBrandDto model_brand_model) {
        this.model_brand_model = model_brand_model;
    }

    //endregion
}
