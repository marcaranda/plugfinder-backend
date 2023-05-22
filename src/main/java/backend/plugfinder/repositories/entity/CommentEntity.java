package backend.plugfinder.repositories.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "comment")
public class CommentEntity {
    //region Comment Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long commnet_id;

    /** Id of the user that has commented*/
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    /** Id of the charger that has been commented */
    @ManyToOne
    @JoinColumn(name = "id_charger")
    private ChargerEntity charger;

    @Column(nullable = false)
    private String comment;

    @Column(columnDefinition = "integer default 0")
    private double points;
    //endregion


    //region Getters and Setters
    public Long getCommnet_id() {
        return commnet_id;
    }

    public void setCommnet_id(Long commnet_id) {
        this.commnet_id = commnet_id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public ChargerEntity getCharger() {
        return charger;
    }

    public void setCharger(ChargerEntity charger) {
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
