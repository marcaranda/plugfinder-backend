package backend.plugfinder.security;

import backend.plugfinder.models.UserModel;
import backend.plugfinder.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepo userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserModel user = userRepository
                .findOneByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("The user " + email + " doesn't exist"));
        return new UserDetailsImpl(user);
    }
}
