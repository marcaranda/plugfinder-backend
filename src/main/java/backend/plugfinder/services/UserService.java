package backend.plugfinder.services;

import backend.plugfinder.models.UserModel;
import backend.plugfinder.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService {
    @Autowired // This annotation allows the dependency injection
    UserRepo userRepo;

    public ArrayList<UserModel> getUsers(){
        return (ArrayList<UserModel>) userRepo.findAll();
    }

    public UserModel saveUser(UserModel userModel){
        return userRepo.save(userModel);
    }
}
