package backend.plugfinder.services;

import backend.plugfinder.models.UserModel;
import backend.plugfinder.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    /**
     * This method sets the premium to a User
     * @param user_id: userId of the user that is getting the premium version
     */
    public void getPremium(Long user_id) {
        UserModel user = userRepo.findById(user_id).orElse(null);
        if(user != null) {
            user.setPremium(true);
            user.setPremium_registration_date(LocalDate.now().toString());
            userRepo.save(user);
        }
        else {
            throw new NullPointerException("El usuario no existe.");
        }
    }
}
