package backend.plugfinder.helpers;

import backend.plugfinder.security.UserDetailsAux;
import io.jsonwebtoken.Claims;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class TokenValidator {
    /**
     * Validates the user id with the token
     * @param user_id The user id to validate
     * @return True if the user id is the same as the one in the token
     */
    public boolean validate_id_with_token(long user_id) {
        Object details = SecurityContextHolder.getContext().getAuthentication().getDetails();
        return details instanceof UserDetailsAux user_details_aux && user_details_aux.getUser_id() == user_id;
    }

    /**
     * Validates if the user is an api user
     * @return True if the user is an api user
     */
    public boolean is_user_api() {
        Object details = SecurityContextHolder.getContext().getAuthentication().getDetails();

        if(details instanceof Claims claims) {
            return claims.get("user_api", Boolean.class);
        }
        else {
            return false;
        }
    }
}
