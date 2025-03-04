package backend.plugfinder.controllers.dto;

import backend.plugfinder.helpers.ModelBrandId;

import java.util.List;


public class ModelBrandDto {
    //region Atributes
    private ModelBrandId id;
    private boolean known;
    private BrandDto brand_model;
    private UserDto user_model;

    private List<ChargerTypeDto> chargers_types;

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

    public List<ChargerTypeDto> getChargers_types() {
        return chargers_types;
    }

    public void setChargers_types(List<ChargerTypeDto> chargers_types) {
        this.chargers_types = chargers_types;
    }
    //endregion
}
