package backend.plugfinder.helpers;

import backend.plugfinder.services.UserService;
import backend.plugfinder.services.models.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class LectorPlugfinderAPI {
    @Autowired
    private UserService user_service;

    public void create_user_api() throws OurException {
        UserModel user = user_service.find_user_by_id(2L);
        if (user == null) {
            user = new UserModel();
            user.setUsername("userAPI");
            user.setReal_name("User API");
            user.setPhone("+34999999999");
            user.setEmail("userapi@gmail.com");
            user.setPassword("userapi");
            user.setBirth_date("01/01/2000");
            user.setUser_api(true);
            user.setZone(Zones.BARCELONA);
            user_service.user_register(user);
        }
    }
}
