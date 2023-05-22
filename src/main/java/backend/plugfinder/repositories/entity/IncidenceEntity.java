package backend.plugfinder.repositories.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "incidence")
public class IncidenceEntity {
    //region Incidence Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long incidence_id;

    /** Id of the user that has post the incidence*/
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    /** Id of the charger that has received the incidence */
    @ManyToOne
    @JoinColumn(name = "id_charger")
    private ChargerEntity charger;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String incidence;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean solved;
    //endregion

    //region Getters and Setters

    public Long getIncidence_id() {
        return incidence_id;
    }

    public void setIncidence_id(Long incidence_id) {
        this.incidence_id = incidence_id;
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
