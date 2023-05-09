package backend.plugfinder.repositories.entity;

import jakarta.persistence.*;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.function.Predicate;

@Entity
@Table(name = "charger")
public class ChargerEntity {
    //region Atributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private long id_charger;

    @Column(nullable = false)
    private int potency;

    @Column(nullable = false)
    private long original_id;

    @Column
    private String adress;

    @Column
    private String electric_current;

    @Column(nullable = false)
    private boolean occupied;

    @Column(nullable = false)
    private boolean is_public;

    private String company;

    @ManyToOne
    @JoinColumn(name = "owner_user")
    private UserEntity owner_user;

    private double latitude;
    private double longitude;


    @ManyToMany
    @JoinTable(name = "types_of_chargers",
            joinColumns = @JoinColumn(name = "charger_id", referencedColumnName = "id_charger"),
            inverseJoinColumns = @JoinColumn(name = "type_id"))
    private List<ChargerTypeEntity> types;
    //endregion

    //region Getters & Setters
    public long getId_charger() {
        return id_charger;
    }

    public void setId_charger(long id_charger) {
        this.id_charger = id_charger;
    }

    public int getPotency() {
        return potency;
    }

    public void setPotency(int potency) {
        this.potency = potency;
    }

    public long getOriginal_id() {
        return original_id;
    }

    public void setOriginal_id(long original_id) {
        this.original_id = original_id;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getElectric_current() {
        return electric_current;
    }

    public void setElectric_current(String electric_current) {
        this.electric_current = electric_current;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public boolean isIs_public() {
        return is_public;
    }

    public void setIs_public(boolean is_public) {
        this.is_public = is_public;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public UserEntity getOwner_user() {
        return owner_user;
    }

    public void setOwner_user(UserEntity owner_user) {
        this.owner_user = owner_user;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public List<ChargerTypeEntity> getTypes() {
        return types;
    }

    public void setTypes(List<ChargerTypeEntity> types) {
        this.types = types;
    }

    //endregion
}
