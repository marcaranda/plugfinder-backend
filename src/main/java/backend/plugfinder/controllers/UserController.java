package backend.plugfinder.controllers;

import backend.plugfinder.models.UserModel;
import backend.plugfinder.services.UserService;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.time.format.DateTimeFormatter;

@RestController
public class UserController {
    @Autowired // This annotation allows the dependency injection.
    UserService userService;

    public UserController() {
        this.userService = new UserService();
    }

    //region Métodos Púbilicos
    //region Registrar Usuario
    /**
     * This method registers a user in the DB.
     * @param usuario - User to be registered.
     * @return UserModel - Registered user.
     */
    /*
    @PostMapping("/registrar")
    public UserModel registrarUsuario(@RequestBody UserModel usuario) {
        try {
            return userService.registrarUsuario(usuario);
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }

    }*/

    /**
     * This method registers a user in the DB.
     * @param usuario - User to be registered.
     * @return UserModel - Registered user.
     */
    @PostMapping("/registrar")
    public ResponseEntity<UserModel> registrarUsuario(@RequestBody UserModel usuario) throws SQLException {
        /* Comprobación validez correo electrónico */
        if(!validarCorreoElectronico(usuario.getCorreo())) {
            throw new SQLException("El correo electrónico no es válido.");
        }
        /* Comprobación validez número de teléfono */
        String tlf = usuario.getTelefono();
        if(tlf != null && !validarNumeroTelefono(usuario.getTelefono())) {
            throw new SQLException("El número de teléfono no es válido.");
        }
        /* Comprobación validez fecha de nacimiento */
        if(!validarFechaNacimiento(usuario.getFecha_nacimiento())) {
            throw new SQLException("La fecha de nacimiento no es válida.");
        }

        /* Encriptado de contraseña -- IMPORTANTE: Al hacer log-in, tenemos que comparar la contraseña introducida con la encriptada en la BD. Para ello,
        usamos la función checkpw(psw, pswCryp) de la clase BCrypt, pero antes tenemos que encriptar la contraseña introducida por el usuario.*/
        usuario.setContrasena(encriptarContrasena(usuario.getContrasena()));

        UserModel newUser = userService.registrarUsuario(usuario);
        if(newUser != null) {
            return ResponseEntity.ok(newUser);
        }
        else {
            throw new SQLException("Error al intentar crear el usuario: ");
        }

    }
    //endregion

    /**
     * This method returns all the users in the DB.
     * @return ArrayList<UserModel> - List of users.
     */
    @GetMapping("/usuarios")
    public ArrayList<UserModel> obtenerUsuarios() {
        return userService.obtenerUsuarios();
    }
    //endregion

    //region Métodos Privados
    /**
     * This method validates the user's email.
     * @param correo - Email to be validated.
     * @return boolean - True if the email is valid, false otherwise.
     */
    private boolean validarCorreoElectronico(String correo) {
        String patron = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        Pattern pattern = Pattern.compile(patron);
        Matcher matcher = pattern.matcher(correo);
        return matcher.matches();
    }

    /**
     * This method validates the user's phone number.
     * @param telefono - Phone number to be validated.
     * @return boolean - True if the phone number is valid, false otherwise.
     */
    private boolean validarNumeroTelefono(String telefono) {
        String patron = "\\+[0-9]{2}\\s[0-9]{9}";
        Pattern pattern = Pattern.compile(patron);
        Matcher matcher = pattern.matcher(telefono);
        return matcher.matches();
    }

    /**
     * This method validates the user's birth date.
     * @param fecha_nacimiento - Birth date to be validated.
     * @return boolean - True if the birth date is valid, false otherwise.
     */
    private boolean validarFechaNacimiento(String fecha_nacimiento) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            LocalDate fecha_nacimiento_act = LocalDate.parse(fecha_nacimiento, formatter);
            LocalDate minima_fecha_nacimiento = LocalDate.of(1900, 1, 1);
            return !fecha_nacimiento_act.isAfter(java.time.LocalDate.now()) || !fecha_nacimiento_act.isBefore(minima_fecha_nacimiento);
        }
        catch (DateTimeParseException e) {
            throw new DateTimeParseException("La fecha de nacimiento no tiene el formato correcto.", fecha_nacimiento, 0);
        }
    }

    /**
     * This method validates the user's password.
     * @param contrasena - Password to be validated.
     * @return boolean - True if the password is valid, false otherwise.
     */
    private String encriptarContrasena(String contrasena) {
        return BCrypt.hashpw(contrasena, BCrypt.gensalt());
    }
    //endregion
}
