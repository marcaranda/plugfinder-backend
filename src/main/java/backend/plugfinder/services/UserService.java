package backend.plugfinder.services;

import backend.plugfinder.models.UserModel;
import backend.plugfinder.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

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

    public UserModel findUserByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    /**
     * This method returns all the users in the DB.
     * @return ArrayList<UserModel> - List of users.
     */
    public ArrayList<UserModel> getUsers() {
        return (ArrayList<UserModel>) userRepo.findAll();
    }

    /**
     * This method deletes a user from the DB.
     * @param user - User to be deleted.
     */
    public void deleteUser(UserModel user) {
        userRepo.delete(user);
    }

    /**
     * This method finds a user by its ID.
     * @param id - ID of the user.
     */
    public UserModel findUserById(Long id) {
        return userRepo.findById(id).orElse(null);
    }
}
