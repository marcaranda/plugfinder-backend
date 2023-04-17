package backend.plugfinder.repositories.entity;

import jakarta.persistence.*;

import java.sql.Time;
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
    private String alias_charger;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private int price_x_kw;

    @Column(nullable = false)
    private Time max_time_charging;

    @Column(nullable = false)
    private boolean is_public;

    private String company;

    @ManyToOne
    @JoinColumn(name = "owner_user")
    private UserEntity owner_user;

    @ManyToOne
    @JoinColumn(name = "latitude")
    @JoinColumn(name = "longitude")
    private LocationEntity location;

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

    public String getAlias_charger() {
        return alias_charger;
    }

    public void setAlias_charger(String alias_charger) {
        this.alias_charger = alias_charger;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getPrice_x_kw() {
        return price_x_kw;
    }

    public void setPrice_x_kw(int price_x_kw) {
        this.price_x_kw = price_x_kw;
    }

    public Time getMax_time_charging() {
        return max_time_charging;
    }

    public void setMax_time_charging(Time max_time_charging) {
        this.max_time_charging = max_time_charging;
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

    public LocationEntity getLocation() {
        return location;
    }

    public void setLocation(LocationEntity location) {
        this.location = location;
    }

    public List<ChargerTypeEntity> getTypes() {
        return types;
    }

    public void setTypes(List<ChargerTypeEntity> types) {
        this.types = types;
    }

    public double getLatitude() {
        return location.getLatitude();
    }

    public double getLongitude() {
        return location.getLongitude();
    }
    //endregion
}
