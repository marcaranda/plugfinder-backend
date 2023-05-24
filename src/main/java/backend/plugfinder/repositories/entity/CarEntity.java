package backend.plugfinder.repositories.entity;

import backend.plugfinder.helpers.CarId;
import jakarta.persistence.*;

@Entity
@Table(name = "Car")
public class CarEntity {
    //region Atributes
    @EmbeddedId
    private CarId id;
    @Column(nullable = false)
    private boolean deleted;
    @Column(nullable = false)
    private boolean default_car;
    @ManyToOne
    @MapsId("user_id")
    @JoinColumn(name = "user_id")
    private UserEntity user_model;
    @ManyToOne
    @JoinColumn(name = "model_name", referencedColumnName = "name")
    @JoinColumn(name = "brand_name", referencedColumnName = "brand_name")
    @JoinColumn(name = "creator_id", referencedColumnName = "user_id")
    @JoinColumn(name = "autonomy", referencedColumnName = "autonomy")
    private ModelBrandEntity model_brand_model;
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

    public boolean isDefault_car() {
        return default_car;
    }

    public void setDefault_car(boolean default_car) {
        this.default_car = default_car;
    }

    public UserEntity getUser_model() {
        return user_model;
    }

    public void setUser_model(UserEntity user_model) {
        this.user_model = user_model;
    }

    public ModelBrandEntity getModel_brand_model() {
        return model_brand_model;
    }

    public void setModel_brand_model(ModelBrandEntity model_brand_model) {
        this.model_brand_model = model_brand_model;
    }

    //endregion
}
