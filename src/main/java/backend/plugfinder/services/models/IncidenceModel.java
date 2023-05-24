package backend.plugfinder.services.models;

public class IncidenceModel {
    //region Incidence attributes

    private Long incidence_id;
    private UserModel user;
    private ChargerModel charger;
    private String title;
    private String incidence;
    private boolean solved;

    //endregion

    //region Getters and Setters

    public Long getIncidence_id() {
        return incidence_id;
    }

    public void setIncidence_id(Long incidence_id) {
        this.incidence_id = incidence_id;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public ChargerModel getCharger() {
        return charger;
    }

    public void setCharger(ChargerModel charger) {
        this.charger = charger;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIncidence() {
        return incidence;
    }

    public void setIncidence(String incidence) {
        this.incidence = incidence;
    }

    public boolean isSolved() {
        return solved;
    }

    public void setSolved(boolean solved) {
        this.solved = solved;
    }

    //endregion
}
