package backend.plugfinder.repositories.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "charger_type")
public class ChargerTypeEntity {
    //region Atributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private long type_id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String electric_current;
    //endregion

    //region Getters & Setters
    public long getType_id() {
        return type_id;
    }

    public void setType_id(long type_id) {
        this.type_id = type_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getElectric_current() {
        return electric_current;
    }

    public void setElectric_current(String electric_current) {
        this.electric_current = electric_current;
    }
    //endregion
}
