package backend.plugfinder.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Brand")
public class BrandModel {
    //region Atributes
    @Id
    @Column(name = "name", unique = true, nullable = false)
    private String name;
    @Column(nullable = false)
    private boolean known;
    //endregion

    //region Getter & Setters
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
    //endregion
}
