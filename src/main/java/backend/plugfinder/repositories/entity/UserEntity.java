package backend.plugfinder.repositories.entity;

import backend.plugfinder.helpers.Zones;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Users")
public class UserEntity {

    //region User Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private long user_id;
    @Column (unique = true, nullable = false)
    private String username;
    @Column (nullable = false)
    private String real_name;
    @Column (unique = true)
    private String phone;
    @Column (unique = true, nullable = false)
    private String email;
    @Column (nullable = false)
    private String password;
    @Column (nullable = false)
    private String birth_date;
    @Column
    private String photo;
    @Column (nullable = false, columnDefinition = "integer default 0")
    long rank_position;
    @Column (nullable = false, columnDefinition = "integer default 0")
    long points;
    @Column (nullable = false, columnDefinition = "float default 0")
    double co2;
    @Column (nullable = false, columnDefinition = "boolean default false" )
    private boolean deleted;
    @Column (nullable = false, columnDefinition = "boolean default false")
    private boolean admin;
    @Column (nullable = false, columnDefinition = "boolean default false")
    private boolean user_api;
    @Enumerated
    @Column(name = "zone", nullable = false, columnDefinition = "VARCHAR(100)")
    private Zones zone;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "favorite_chargers",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "charger_id", referencedColumnName = "id_charger"))
    private List<ChargerEntity> favorite_chargers;

    //region Premium attributes
    /** Here we will define the attributes for a premium user*/
    @Column(nullable = false, columnDefinition = "boolean default false")
    boolean premium;
    @Column (columnDefinition = "VARCHAR(255) CHECK (premium = true)")
    String premium_registration_date;
    @Column (columnDefinition = "VARCHAR(255) CHECK (premium = true)")
    String premium_drop_date;
    //endregion

    //endregion

    //region Getters y Setters
    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public long getRank_position() {
        return rank_position;
    }

    public void setRank_position(long rank_position) {
        this.rank_position = rank_position;
    }

    public long getPoints() {
        return points;
    }

    public void setPoints(long points) {
        this.points = points;
    }

    public double getCo2() {
        return co2;
    }

    public void setCo2(double co2) {
        this.co2 = co2;
    }

    public boolean isAdmin() {
        return admin;
    }

    public boolean isUser_api() {
        return user_api;
    }

    public void setUser_api(boolean user_api) {
        this.user_api = user_api;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean isPremium() {
        return premium;
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
    }

    public String getPremium_registration_date() {
        return premium_registration_date;
    }

    public void setPremium_registration_date(String premium_registration_date) {
        this.premium_registration_date = premium_registration_date;
    }

    public Zones getZone() {
        return zone;
    }

    public void setZone(Zones zone) {
        this.zone = zone;
    }

    public String getPremium_drop_date() {
        return premium_drop_date;
    }

    public void setPremium_drop_date(String premium_drop_date) {
        this.premium_drop_date = premium_drop_date;
    }

    public List<ChargerEntity> getFavorite_chargers() {
        return favorite_chargers;
    }

    public void setFavorite_chargers(List<ChargerEntity> favorite_chargers) {
        this.favorite_chargers = favorite_chargers;
    }

    //endregion
}
