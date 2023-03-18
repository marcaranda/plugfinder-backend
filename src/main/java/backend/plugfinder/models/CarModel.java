package backend.plugfinder.models;

import backend.plugfinder.helpersId.CarId;
import jakarta.persistence.*;

@Entity
@Table(name = "Car")
public class CarModel {
    //region Atributes
    @EmbeddedId
    private CarId id;
    @Column(nullable = false)
    private String alias;
    @Column(nullable = false)
    private String autonomy;
    @ManyToOne
    @MapsId("user_id")
    @JoinColumn(name = "user_id")
    private UserModel user_model;
    @ManyToOne
    @JoinColumn(name = "model_name", referencedColumnName = "name")
    @JoinColumn(name = "brand_name", referencedColumnName = "brand_name")
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

    public String getAutonomy() {
        return autonomy;
    }

    public void setAutonomy(String autonomy) {
        this.autonomy = autonomy;
    }

    public UserModel getUserModel() {
        return user_model;
    }

    public void setUserModel(UserModel user_model) {
        this.user_model = user_model;
    }

    public ModelBrandModel getModelBrandModel() {
        return model_brand_model;
    }

    public void setModelBrandModel(ModelBrandModel model_brand_model) {
        this.model_brand_model = model_brand_model;
    }
    //endregion
}
