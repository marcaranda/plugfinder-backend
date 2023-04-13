package backend.plugfinder.repositories.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Brand")
public class BrandEntity {
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
