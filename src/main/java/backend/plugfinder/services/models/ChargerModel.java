package backend.plugfinder.services.models;

import java.util.List;
import java.util.Objects;

public class ChargerModel {

    private long id_charger;

    private int potency;

    private long original_id;

    private String adress;

    private String electric_current;

    private boolean occupied;

    private boolean is_public;

    private String company;

    private boolean active;

    private Double price;

    private UserModel owner_user;

    private Double latitude;

    private Double longitude;

    private List<ChargerTypeModel> types;

    private String charger_photo;

    private String charger_photo_base64;

    private double score;

    //region Constructores, getters y setters
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

    public UserModel getOwner_user() {
        return owner_user;
    }

    public void setOwner_user(UserModel owner_user) {
        this.owner_user = owner_user;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public List<ChargerTypeModel> getTypes() {
        return types;
    }

    public void setTypes(List<ChargerTypeModel> types) {
        this.types = types;
    }

    public String getCharger_photo() {
        return charger_photo;
    }

    public void setCharger_photo(String charger_photo) {
        this.charger_photo = charger_photo;
    }

    public String getCharger_photo_base64() {
        return charger_photo_base64;
    }

    public void setCharger_photo_base64(String charger_photo_base64) {
        this.charger_photo_base64 = charger_photo_base64;
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

    //region Overrides
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChargerModel that = (ChargerModel) o;
        return id_charger == that.id_charger;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_charger);
    }

    //endregion
}
