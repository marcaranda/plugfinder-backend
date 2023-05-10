package backend.plugfinder.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDetailsAux {
    private Long user_id;
    private Boolean user_api;
}
