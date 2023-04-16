package backend.plugfinder.services;

import backend.plugfinder.helpers.OurException;
import backend.plugfinder.helpers.TokenValidator;
import backend.plugfinder.repositories.entity.UserEntity;
import backend.plugfinder.services.models.UserModel;
import backend.plugfinder.repositories.UserRepo;
import org.mindrot.jbcrypt.BCrypt;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {
    @Autowired // This annotation allows the dependency injection
    UserRepo user_repo;

    /**
     * This method registers a user in the DB.
     * @param user - User to be registered.
     * @return UserModel - Registered user.
     */
    public UserModel user_register(UserModel user) throws OurException {
        ModelMapper modelMapper = new ModelMapper();

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

        return modelMapper.map(user_repo.save(modelMapper.map(user, UserEntity.class)), UserModel.class);
    }

    /**
     * This method returns all the users in the DB.
     * @return ArrayList<UserModel> - List of users.
     */
    public ArrayList<UserModel> get_users() {
        ModelMapper model_mapper = new ModelMapper();
        ArrayList<UserModel> users = new ArrayList<>();
        user_repo.findAll().forEach(elementB -> users.add(model_mapper.map(elementB, UserModel.class)));
        return users;
    }

    /**
     * This method deletes a user from the DB.
     * @param user - User to be deleted.
     */
    public void delete_user(Long user_id) throws OurException {
        if(new TokenValidator().validate_id_with_token(user_id)) {
            ModelMapper model_mapper = new ModelMapper();

            UserModel user = find_user_by_id(user_id);
            if(user == null || user.isDeleted()) {
                throw new OurException("Error al intentar eliminar el usuario. El usuario no existe.");
            }
            user.setDeleted(true);
            user_repo.save(model_mapper.map(user, UserEntity.class));
        }
        else {
            throw new OurException("El id especificado es diferente al recibido en el token");
        }
    }

    /**
     * This method finds a user by its ID.
     * @param id - ID of the user.
     */
    public UserModel find_user_by_id(Long id) {
        ModelMapper model_mapper = new ModelMapper();


        if (user_repo.findById(id).orElse(null) != null) {
            return model_mapper.map(user_repo.findById(id).orElse(null), UserModel.class);
        } else {
            // manejar el caso en que el usuario no existe
            return null;
        }
    }

    /**
     * This method sets the premium to a User
     * @param user_id: userId of the user that is getting the premium version
     */
    public void get_premium(Long user_id) throws OurException {
        if(new TokenValidator().validate_id_with_token(user_id)) {
            UserModel user = find_user_by_id(user_id);
            if(!user.isPremium()) {
                ModelMapper model_mapper = new ModelMapper();

                user.setPremium(true);
                user.setPremium_registration_date(LocalDate.now().toString());
                user.setPremium_drop_date(null);
                user_repo.save(model_mapper.map(user, UserEntity.class));
            }
            else {
                throw new OurException("El usuario ya es premium");
            }
        }
        else {
            throw new OurException("El user_id enviado es diferente al especificado en el token");
        }
    }

    /**
     * This method unsubscribe a user of the premium version
     * @param userId: userId of the user that is being unsubscribed of the premium version
     */
    public void unsubscribe_premium(Long user_id) throws OurException {
        if(new TokenValidator().validate_id_with_token(user_id)) {
            ModelMapper model_mapper = new ModelMapper();

            UserModel user = find_user_by_id(user_id);

            user.setPremium(false);
            user.setPremium_drop_date(LocalDate.now().toString());
            user_repo.save(model_mapper.map(user, UserEntity.class));
        }
        else {
            throw new OurException("El user_id enviado es diferente al especificado en el token");
        }
    }

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
