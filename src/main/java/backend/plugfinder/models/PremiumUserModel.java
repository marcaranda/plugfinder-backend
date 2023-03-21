package backend.plugfinder.models;

import jakarta.persistence.Column;

import java.util.Date;

public class PremiumUserModel extends UserModel {
    /** User id */
    @Column(unique = true, nullable = false)
    int user_id;

    /** Photo */

    /** Premium registration date */
    @Column(nullable = false)
    String premium_registration_date;

    /** Premium drop date */
    @Column(nullable = false)
    String premium_drop_date;

    public int getIdUser() {
        return user_id;
    }

    public void setIdUser(int idUser) {
        this.user_id = idUser;
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
}
