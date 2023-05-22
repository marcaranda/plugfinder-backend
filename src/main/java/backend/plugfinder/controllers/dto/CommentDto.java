package backend.plugfinder.controllers.dto;

public class CommentDto {
    //region Comment attributes

    private Long comment_id;

    private UserDto user;

    private ChargerDto charger;

    private String comment;

    private double points;

    //endregion

    //region Getters and Setters

    public Long getComment_id() {
        return comment_id;
    }

    public void setComment_id(Long comment_id) {
        this.comment_id = comment_id;
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
