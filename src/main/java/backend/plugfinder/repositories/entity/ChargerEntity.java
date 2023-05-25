package backend.plugfinder.repositories.entity;

import jakarta.persistence.*;

import java.util.List;

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

    @Column(nullable = false)
    private boolean active;

    @ManyToOne
    @JoinColumn(name = "owner_user")
    private UserEntity owner_user;

    private double latitude;
    private double longitude;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "types_of_chargers",
            joinColumns = @JoinColumn(name = "charger_id", referencedColumnName = "id_charger"),
            inverseJoinColumns = @JoinColumn(name = "type_id"))
    private List<ChargerTypeEntity> types;
    @Column
    private String charger_photo;

    @Column (columnDefinition = "double default value 0.0")
    private double score;
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

    public String getCharger_photo() {
        return charger_photo;
    }

    public void setCharger_photo(String charger_photo) {
        this.charger_photo = charger_photo;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    //endregion
}
