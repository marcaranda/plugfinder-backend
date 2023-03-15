package backend.plugfinder.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Brand")
public class BrandModel {
    @Id
    @Column(unique = true, nullable = false)
    private String name;
    @Column(nullable = false)
    private boolean known;
    /*@OneToMany
    private List<ModelBrandModel> models;*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isKnown() {
        return known;
    }

    public void setKnown(boolean known) {
        this.known = known;
    }

    /*public List<ModelBrandModel> getModels() {
        return models;
    }

    public void setModels(List<ModelBrandModel> models) {
        this.models = models;
    }*/
}
