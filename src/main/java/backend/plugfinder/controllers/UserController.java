package backend.plugfinder.controllers;

import backend.plugfinder.controllers.dto.ChargerDto;
import backend.plugfinder.controllers.dto.UserDto;
import backend.plugfinder.helpers.OurException;
import backend.plugfinder.helpers.Zones;
import backend.plugfinder.services.UserService;
import backend.plugfinder.services.models.UserModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
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
    @PreAuthorize("@securityService.not_userAPI()")
    public void delete_user(@PathVariable Long user_id) throws OurException {
            user_service.delete_user(user_id);
    }

    /**
     * This method sets the premium to a User
     * @param user_id: userId of the user that is getting the premium version
     */
    @PutMapping("/{user_id}/premium")
    @PreAuthorize("@securityService.not_userAPI()")
    public void get_premium(@PathVariable("user_id") Long user_id) throws OurException {
        user_service.get_premium(user_id);
    }

    /**
     * This method sets the premium to a User
     * @param user_id: userId of the user that is getting the premium version with points
     */
    @PutMapping("/{user_id}/premium_with_points")
    @PreAuthorize("@securityService.not_userAPI()")
    public void set_premium_points(@PathVariable("user_id") Long user_id) throws OurException {
        user_service.set_premium_with_points(user_id);
    }

    /**
     * This method unsubscribes a user of the premium version t
     * @param user_id: userId of the user that is being unsubscribed of the premium version
     */
    @PutMapping("/{user_id}/unsubscribePremium")
    @PreAuthorize("@securityService.not_userAPI()")
    public void stop_premium(@PathVariable("user_id") Long user_id) throws OurException {
        user_service.unsubscribe_premium(user_id);
    }

    //region Cargadores favoritos
    @GetMapping("/{user_id}/favorites")
    @PreAuthorize("@securityService.not_userAPI()")
    public ArrayList<ChargerDto> get_chargers_favorites(@PathVariable("user_id") Long user_id) throws OurException {
        ModelMapper model_mapper = new ModelMapper();
        ArrayList<ChargerDto> chargers = (ArrayList<ChargerDto>) user_service.get_chargers_favorites(user_id).stream()
                .map(elementB -> model_mapper.map(elementB, ChargerDto.class))
                .collect(Collectors.toList());

        return chargers;
    }

    @PutMapping("/{user_id}/addfavorite/{charger_id}")
    @PreAuthorize("@securityService.not_userAPI()")
    public void add_favorite(@PathVariable("user_id") Long user_id, @PathVariable("charger_id") Long charger_id) throws OurException {
        user_service.add_favorite(user_id, charger_id);
    }

    @PutMapping("/{user_id}/deletefavorite/{charger_id}")
    @PreAuthorize("@securityService.not_userAPI()")
    public void delete_favorite(@PathVariable("user_id") Long user_id, @PathVariable("charger_id") Long charger_id) throws OurException {
        user_service.delete_favorite(user_id, charger_id);
    }
    //endregion

    //region Last Chats
    @GetMapping("/{user_id}/chats")
    @PreAuthorize("@securityService.not_userAPI()")
    public ArrayList<UserDto> get_last_chats(@PathVariable("user_id") Long user_id) throws OurException {
        ModelMapper model_mapper = new ModelMapper();
        ArrayList<UserDto> chargers = (ArrayList<UserDto>) user_service.get_last_chats(user_id).stream()
                .map(elementB -> model_mapper.map(elementB, UserDto.class))
                .collect(Collectors.toList());

        return chargers;
    }
    //endregion

    //region Perfil del usuario
    /**
     * This method returns the profile of a user.
     * @param user_id - Id of the user.
     * @return UserDto - User profile.
     */
    @GetMapping("/{user_id}/profile")
    @PreAuthorize("@securityService.not_userAPI()")
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
    @PreAuthorize("@securityService.not_userAPI()")
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
    @PreAuthorize("@securityService.not_userAPI()")
    public ArrayList<UserDto> get_users() {
        ModelMapper model_mapper = new ModelMapper();
        ArrayList<UserDto> users = (ArrayList<UserDto>) user_service.get_users().stream()
                .map(elementB ->model_mapper.map(elementB, UserDto.class))
                .collect(Collectors.toList());

        return users;
    }

    @GetMapping("/ranking")
    @PreAuthorize("@securityService.not_userAPI()")
    public ArrayList<UserDto> get_ranking(@RequestParam(required = false, value = "zone") Zones zone) {
        ModelMapper model_mapper = new ModelMapper();

        ArrayList<UserDto> users = (ArrayList<UserDto>) user_service.get_ranking(zone).stream()
                .map(elementB ->model_mapper.map(elementB, UserDto.class))
                .collect(Collectors.toList());

        return users;
    }

    //endregion
}
