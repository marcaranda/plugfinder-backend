package backend.plugfinder.models;

import java.util.Date;

public class PremiumUserModel extends ClientModel {
    /** Photo */

    /** Premium registration date */
    Date premium_registration_date;

    /** Premium drop date */
    Date premium_drop_date;

    public Date getPremium_registration_date() {
        return premium_registration_date;
    }

    public void setPremium_registration_date(Date premium_registration_date) {
        this.premium_registration_date = premium_registration_date;
    }

    public Date getPremium_drop_date() {
        return premium_drop_date;
    }

    public void setPremium_drop_date(Date premium_drop_date) {
        this.premium_drop_date = premium_drop_date;
    }
}
