package backend.plugfinder.helpers;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class TokenValidator {
    public boolean validate_id_with_token(long user_id) {
        Authentication uath = SecurityContextHolder.getContext().getAuthentication();
        return (long) uath.getDetails() == user_id;
    }
}
