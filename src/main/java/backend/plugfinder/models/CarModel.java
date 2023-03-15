package backend.plugfinder.models;

import jakarta.persistence.*;

@Entity
@Table(name = "Car")
public class CarModel {
    @Id
    @Column(unique = true, nullable = false)
    private String license;
    @Column(nullable = false)
    private String alias;
    @Column(nullable = false)
    private String autonomy;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id_usuario")
    private UserModel userModel;
    @ManyToOne
    @JoinColumn(name = "model_name", referencedColumnName = "name")
    @JoinColumn(name = "brand_name", referencedColumnName = "brand_name")
    private ModelBrandModel modelBrandModel;


    /*Getter & Setters*/
    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
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
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public ModelBrandModel getModelBrandModel() {
        return modelBrandModel;
    }

    public void setModelBrandModel(ModelBrandModel modelBrandModel) {
        this.modelBrandModel = modelBrandModel;
    }
}
