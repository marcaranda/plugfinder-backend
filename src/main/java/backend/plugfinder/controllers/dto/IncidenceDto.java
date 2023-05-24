package backend.plugfinder.controllers.dto;

public class IncidenceDto {
    //region Incidence attributes
    private Long incidence_id;
    private UserDto user;
    private ChargerDto charger;
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

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public ChargerDto getCharger() {
        return charger;
    }

    public void setCharger(ChargerDto charger) {
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
