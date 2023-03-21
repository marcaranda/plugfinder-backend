package backend.plugfinder.models;

import jakarta.persistence.Column;

public class BasicUserModel extends UserModel {
    /** User id */
    @Column(unique = true, nullable = false)
    int user_id;

    public int getIdUser() {
        return user_id;
    }

    public void setIdUser(int idUser) {
        this.user_id = idUser;
    }
}
