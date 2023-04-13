package backend.plugfinder.security;

import backend.plugfinder.repositories.UserRepo;
import backend.plugfinder.services.models.UserModel;
import org.modelmapper.ModelMapper;
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
        ModelMapper model_mapper = new ModelMapper();

        UserModel user = model_mapper.map(userRepository.findOneByEmail(email), UserModel.class);
        if(user == null) throw new UsernameNotFoundException("The user " + email + " doesn't exist");
        return new UserDetailsImpl(user);
    }
}
