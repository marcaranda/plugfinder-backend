package backend.plugfinder.services;

import backend.plugfinder.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired // This annotation allows the dependency injection
    UserRepo userRepo;
}
