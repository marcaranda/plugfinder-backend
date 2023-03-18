package backend.plugfinder.services;

import backend.plugfinder.models.UserModel;
import backend.plugfinder.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserService {
    @Autowired // This annotation allows the dependency injection
    UserRepo userRepo;

    /**
     * This method registers a user in the DB.
     * @param user - User to be registered.
     * @return UserModel - Registered user.
     */
    public UserModel userRegister(UserModel user) {
        return userRepo.save(user);
    }

    Optional<UserModel> findUserByEmail(String email) {
        return userRepo.findOneByEmail(email);
    }

    /**
     * This method returns all the users in the DB.
     * @return ArrayList<UserModel> - List of users.
     */
    public ArrayList<UserModel> getUsers() {
        return (ArrayList<UserModel>) userRepo.findAll();
    }

}
