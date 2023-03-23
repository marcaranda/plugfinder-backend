package backend.plugfinder.controllers;

import backend.plugfinder.models.OurException;
import backend.plugfinder.models.UserModel;
import backend.plugfinder.services.UserService;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired // This annotation allows the dependency injection.
    UserService userService;

    //region Métodos Púbilicos
    //region Registrar Usuario

    /**
     * This method registers a user in the DB.
     * @param user - User to be registered.
     * @return UserModel - Registered user.
     */
    @PostMapping("/register")
    public ResponseEntity<UserModel> userRegister(@RequestBody UserModel user) throws SQLException, OurException {
        /* Comprobación validez correo electrónico */
        if(!validateEmail(user.getEmail())) {
            throw new OurException("El correo electrónico no es válido.");
        }
        /* Comprobación validez número de teléfono */
        String tlf = user.getPhone();
        if(tlf != null && !validatePhoneNumber(tlf)) {
            throw new OurException("El número de teléfono no es válido.");
        }
        /* Comprobación validez fecha de nacimiento */
        if(!validateBirthDate(user.getBirth_date())) {
            throw new OurException("La fecha de nacimiento no es válida.");
        }

        /* Encriptado de contraseña -- IMPORTANTE: Al hacer log-in, tenemos que comparar la contraseña introducida con la encriptada en la BD. Para ello,
        usamos la función checkpw(psw, pswCryp) de la clase BCrypt, pero antes tenemos que encriptar la contraseña introducida por el usuario.*/
        user.setPassword(encryptPassowrd(user.getPassword()));

        UserModel newUser = userService.userRegister(user);
        if(newUser != null) {
            return ResponseEntity.ok(newUser);
        }
        else {
            throw new SQLException("Error al intentar crear el usuario: ");
        }

    }
    //endregion

    /**
     *This method deletes a user from the DB.
     * @param id - Id of the user to be deleted.
     */
    @PostMapping ("/delete")
    public void deleteUser(@RequestParam Long id) throws OurException {
        try {
            UserModel user = userService.findUserById(id);
            userService.deleteUser(user);
            //System.out.println(user.isDeleted());
        }catch (Exception e){
           throw new OurException("Error al intentar eliminar el usuario");
        }
    }

    /**
     * This method returns all the users in the DB.
     * @return ArrayList<UserModel> - List of users.
     */
    @GetMapping
    public ArrayList<UserModel> getUsers() {
        return userService.getUsers();
    }
    //endregion

    //region Métodos Privados
    /**
     * This method validates the user's email.
     * @param email - Email to be validated.
     * @return boolean - True if the email is valid, false otherwise.
     */
    private boolean validateEmail(String email) {
        String patron = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        Pattern pattern = Pattern.compile(patron);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * This method validates the user's phone number.
     * @param phone - Phone number to be validated.
     * @return boolean - True if the phone number is valid, false otherwise.
     */
    private boolean validatePhoneNumber(String phone) {
        String patron = "^\\+(?:[0-9] ?){6,14}[0-9]$";
        Pattern pattern = Pattern.compile(patron);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    /**
     * This method validates the user's birth date.
     * @param birth_date - Birth date to be validated.
     * @return boolean - True if the birth date is valid, false otherwise.
     */
    private boolean validateBirthDate(String birth_date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            LocalDate fecha_nacimiento_act = LocalDate.parse(birth_date, formatter);
            LocalDate minima_fecha_nacimiento = LocalDate.of(1900, 1, 1);
            return !fecha_nacimiento_act.isAfter(java.time.LocalDate.now()) || !fecha_nacimiento_act.isBefore(minima_fecha_nacimiento);
        }
        catch (DateTimeParseException e) {
            throw new DateTimeParseException("La fecha de nacimiento no tiene el formato correcto.", birth_date, 0);
        }
    }

    /**
     * This method validates the user's password.
     * @param password - Password to be validated.
     * @return boolean - True if the password is valid, false otherwise.
     */
    private String encryptPassowrd(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
    //endregion
}
