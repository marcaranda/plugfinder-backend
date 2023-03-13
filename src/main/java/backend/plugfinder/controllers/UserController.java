package backend.plugfinder.controllers;

import backend.plugfinder.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired // This annotation allows the dependency injection.
    UserService userService;
}
