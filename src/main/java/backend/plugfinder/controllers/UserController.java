package backend.plugfinder.controllers;

import backend.plugfinder.controllers.dto.UserDto;
import backend.plugfinder.helpers.TokenValidator;
import backend.plugfinder.helpers.OurException;
import backend.plugfinder.services.UserService;
import backend.plugfinder.services.models.UserModel;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired // This annotation allows the dependency injection.
    UserService user_service;

    //region Métodos Púbilicos
    //region Registrar Usuario

    /**
     * This method registers a user in the DB.
     * @param user - User to be registered.
     * @return UserModel - Registered user.
     */
    @PostMapping("/register")
    public ResponseEntity<UserDto> user_register(@RequestBody UserDto user) throws OurException {
        ModelMapper model_mapper = new ModelMapper();

        UserDto newUser = model_mapper.map(user_service.user_register(model_mapper.map(user, UserModel.class)), UserDto.class);
        if(newUser != null) {
            return ResponseEntity.ok(newUser);
        }
        else {
            throw new OurException("Error al intentar crear el usuario: ");
        }
    }
    //endregion

    /**
     *This method deletes a user from the DB.
     * @param user_id - Id of the user to be deleted.
     */
    @PutMapping ("/delete/{user_id}")
    public void delete_user(@PathVariable Long user_id) throws OurException {
            user_service.delete_user(user_id);
    }

    /**
     * This method sets the premium to a User
     * @param user_id: userId of the user that is getting the premium version
     */
    @PutMapping("/{user_id}/premium")
    public void get_premium(@PathVariable("user_id") Long user_id) throws OurException {
        user_service.get_premium(user_id);
    }

    /**
     * This method unsubscribes a user of the premium version t
     * @param user_id: userId of the user that is being unsubscribed of the premium version
     */
    @PutMapping("/{user_id}/unsubscribePremium")
    public void stop_premium(@PathVariable("user_id") Long user_id) throws OurException {
        user_service.unsubscribe_premium(user_id);
    }

    @PutMapping("/{user_id}/addfavorite/{charger_id}")
    public void add_favorite(@PathVariable("user_id") Long user_id, @PathVariable("charger_id") Long charger_id){
        user_service.add_favorite(user_id, charger_id);
    }

    //region Perfil del usuario
    /**
     * This method returns the profile of a user.
     * @param user_id - Id of the user.
     * @return UserDto - User profile.
     */
    @GetMapping("/{user_id}/profile")
    public UserDto view_profile(@PathVariable("user_id") Long user_id) throws OurException {
        ModelMapper model_mapper = new ModelMapper();
        UserDto user = model_mapper.map(user_service.view_profile(user_id), UserDto.class);
        if(user == null) throw new OurException("El usuario no exsite");
        return user;
    }

    /**
     * This method updates the profile of a user.
     * @param user - User to be updated.
     * @return UserDto - Updated user profile.
     */
    @PutMapping("/profile/edit")
    public UserDto update_profile(@RequestBody UserDto user) throws OurException {
        ModelMapper model_mapper = new ModelMapper();
        UserDto updated_user = model_mapper.map(user_service.update_user(model_mapper.map(user, UserModel.class)), UserDto.class);
        if(updated_user == null) throw new OurException("El usuario no exsite");
        return updated_user;
    }

    //endregion

    /**
     * This method returns all the users in the DB.
     * @return ArrayList<UserModel> - List of users.
     */
    @GetMapping
    public ArrayList<UserDto> get_users() {
        ModelMapper model_mapper = new ModelMapper();
        ArrayList<UserDto> users = (ArrayList<UserDto>) user_service.get_users().stream()
                .map(elementB ->model_mapper.map(elementB, UserDto.class))
                .collect(Collectors.toList());

        return users;
    }
    //endregion
}
