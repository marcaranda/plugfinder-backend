package backend.plugfinder.controllers.dto;

import backend.plugfinder.services.models.ChargerModel;
import backend.plugfinder.services.models.UserModel;

public class CommentDto {
    //region Comment attributes

    private Long commnet_id;

    private UserModel user;

    private ChargerModel charger;

    private String comment;

    private double points;

    //endregion

    //region Getters and Setters

    public Long getCommnet_id() {
        return commnet_id;
    }

    public void setCommnet_id(Long commnet_id) {
        this.commnet_id = commnet_id;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }


    //endregion
}
