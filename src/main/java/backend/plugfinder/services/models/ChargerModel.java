package backend.plugfinder.services.models;

import java.util.List;

public class ChargerModel {

    private long id_charger;

    private int potency;

    private long original_id;

    private String adress;

    private String electric_current;

    private boolean occupied;

    private boolean is_public;

    private String company;

    private UserModel owner_user;

    private LocationModel location;

    private List<ChargerTypeModel> types;

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

    public LocationModel getLocation() {
        return location;
    }

    public void setLocation(LocationModel location) {
        this.location = location;

    }

    public List<ChargerTypeModel> getTypes() {
        return types;
    }

    public void setTypes(List<ChargerTypeModel> types) {
        this.types = types;
    }
    //endregion
}
