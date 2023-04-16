package backend.plugfinder.services.models;

import jakarta.persistence.*;

public class UserModel {

    //region User Attributes
    private long user_id;
    private String username;
    private String real_name;
    private String phone;
    private String email;
    private String password;
    private String birth_date;
    long rank_position;
    long points;
    double co2;
    private boolean deleted;
    private boolean admin;

    //region Premium attributes
    /** Here we will define the attributes for a premium user*/
    boolean premium;
    String premium_registration_date;
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

    public String getPremium_drop_date() {
        return premium_drop_date;
    }

    public void setPremium_drop_date(String premium_drop_date) {
        this.premium_drop_date = premium_drop_date;
    }

    //endregion
}