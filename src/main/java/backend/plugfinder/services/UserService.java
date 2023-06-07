package backend.plugfinder.services;

import backend.plugfinder.helpers.OurException;
import backend.plugfinder.helpers.TokenValidator;
import backend.plugfinder.helpers.Zones;
import backend.plugfinder.repositories.entity.UserEntity;
import backend.plugfinder.services.models.ChargerModel;
import backend.plugfinder.services.models.CarModel;
import backend.plugfinder.services.models.UserModel;
import backend.plugfinder.repositories.UserRepo;
import org.mindrot.jbcrypt.BCrypt;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {
    @Autowired // This annotation allows the dependency injection
    UserRepo user_repo;
    @Autowired
    ChargerService charger_service;
    @Autowired
    AmazonS3Service amazonS3Service;

    @Autowired
    CarService car_service;


    //region Registrar usuario
    /**
     * This method registers a user in the DB.
     * @param user - User to be registered.
     * @return UserModel - Registered user.
     */
    public UserModel user_register(UserModel user) throws OurException {
        ModelMapper model_mapper = new ModelMapper();

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

        /* Subimos la imagen pasada en formato base 64 al bucket s3 de amazon y guardamos su url pública en el atributo photo del usuario */
        if(user.getPhoto_base64() != null) {
            String public_url_photo = amazonS3Service.upload_file(user.getUsername(), user.getPhoto_base64());
            user.setPhoto(public_url_photo);
        }

        return model_mapper.map(user_repo.save(model_mapper.map(user, UserEntity.class)), UserModel.class);
    }
    //endregion

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

    public ArrayList<UserModel> get_ranking(Zones zone) {
        ModelMapper model_mapper = new ModelMapper();
        ArrayList<UserModel> users = new ArrayList<>();
        if (zone != null){
            user_repo.findOrderRankingByZone(zone).forEach(elementB -> users.add(model_mapper.map(elementB, UserModel.class)));
        }else{
            user_repo.findOrderRanking().forEach(elementB -> users.add(model_mapper.map(elementB, UserModel.class)));
        }
        return users;
    }


    //region Eliminar usuario
    /**
     * This method deletes a user from the DB.
     * @param user_id - Id of the user to be deleted.
     */
    public void delete_user(Long user_id) throws OurException {
        if(new TokenValidator().validate_id_with_token(user_id)) {
            ModelMapper model_mapper = new ModelMapper();

            UserModel user = find_user_by_id(user_id);
            if(user == null || user.isDeleted()) {
                throw new OurException("Error al intentar eliminar el usuario. El usuario no existe.");
            }

            //Agafem els cotxes del usuari per eliminar-los
            ArrayList<CarModel> user_cars = car_service.get_cars(user_id);
            for(CarModel c : user_cars) {
                //Eliminem els cotxes del usuari
                car_service.delete_car(c.getId().getLicense(), user_id);
            }

            user.setDeleted(true);
            user_repo.save(model_mapper.map(user, UserEntity.class));
        }
        else {
            throw new OurException("El id especificado es diferente al recibido en el token");
        }
    }
    //endregion

    //region Perfil del ususario
    /**
     * This method returns the profile of a user.
     * @param user_id - Id of the user.
     * @return UserModel - Profile of the user.
     */
    public UserModel view_profile(Long user_id) throws OurException {
        if(new TokenValidator().validate_id_with_token(user_id)) {
            //Si la validació del token ha tingut éxit, vol dir que vull veure el meu propi perfil, per tant, retorno tota la info
            return find_user_by_id(user_id);
        }
        else {
            //Si es fals, vol dir que vull veure el perfil d'un altre usuari, per tant retorno només les dades no sensibles.
            UserModel user = find_user_by_id(user_id);
            if(user == null || user.isDeleted()) {
                throw new OurException("Error al intentar ver el perfil del usuario. El usuario no existe.");
            }
            UserModel user_without_delicated_data = new UserModel();
            user_without_delicated_data.setUsername(user.getUsername());
            user_without_delicated_data.setReal_name(user.getReal_name());
            user_without_delicated_data.setBirth_date(user.getBirth_date());
            user_without_delicated_data.setPhoto(user.getPhoto());
            return user_without_delicated_data;
        }
    }
    /**
     * This method returns the profile of a user.
     * @return UserModel - Profile of the user.
     */
    public UserModel update_user(UserModel user) throws OurException {
        if(new TokenValidator().validate_id_with_token(user.getUser_id())) {
            ModelMapper model_mapper = new ModelMapper();

            if(user == null || user.isDeleted()) {
                throw new OurException("Error al intentar actualizar el usuario. El usuario no existe.");
            }
            else {
                //Agafem l'usuari que tenim guardat a la BD
                UserModel user_to_be_updated = model_mapper.map(user_repo.findById(user.getUser_id()), UserModel.class);

                //Si el correu és diferent l'actualitzem
                if(!user_to_be_updated.getEmail().equals(user.getEmail())) {
                    if(!validateEmail(user.getEmail())) {
                        throw new OurException("El correo electrónico no es válido.");
                    }
                }

                //Si el tlf és diferent l'actualitzem
                String user_tlf = user.getPhone();
                if(!user_to_be_updated.getPhone().equals(user_tlf)) {
                    if(user_tlf != null && !validatePhoneNumber(user_tlf)) {
                        throw new OurException("El número de teléfono no es válido.");
                    }
                }

                //Si la data de naixement és diferent l'actualitzem
                if(!user_to_be_updated.getBirth_date().equals(user.getBirth_date())) {
                    if(!validateBirthDate(user.getBirth_date())) {
                        throw new OurException("La fecha de nacimiento no es válida.");
                    }
                }

                //Si la contrasenya és diferent l'actualtizem
                if(!user_to_be_updated.getPassword().equals(user.getPassword())) {
                    //Encriptem la contraseña
                    user.setPassword(encryptPassowrd(user.getPassword()));
                }
                else {
                    user.setPassword(user_to_be_updated.getPassword());
                }

                //Si la foto de perfil és diferent l'actualitzem
                if(user.getPhoto_base64() != null) {
                    if(user_to_be_updated.getPhoto() != null) {
                        //Eliminem la foto anterior
                        amazonS3Service.delete_file(user_to_be_updated.getUsername());
                    }
                    //Guardem la nova foto
                    if(user.getUsername() != null) {
                        user.setPhoto(amazonS3Service.upload_file(user.getUsername(), user.getPhoto_base64()));
                    }
                    else {
                        user.setPhoto(amazonS3Service.upload_file(user_to_be_updated.getUsername(), user.getPhoto_base64()));
                    }
                }

                //Com la crida al métode save està especificant l'id de l'usuari, no s'està fent un insert, sinó un update
                return model_mapper.map(user_repo.save(model_mapper.map(user, UserEntity.class)), UserModel.class);
            }
        }
        else {
            throw new OurException("El user_id enviado es diferente al especificado en el token");
        }

    }
    //endregion

    //region Last Chats
    public ArrayList<UserModel> get_last_chats(Long user_id) throws OurException {
        if(new TokenValidator().validate_id_with_token(user_id)) {
            UserModel user = find_user_by_id(user_id);

            if (!(user == null)) {
                return (ArrayList<UserModel>) user.getLast_chats();
            }
            else {
                throw new OurException("El usuario no existe");
            }
        }
        else {
            throw new OurException("El user_id enviado es diferente al especificado en el token");
        }
    }

    public void save_last_chats(UserModel source_user, UserModel target_user) {
        ModelMapper model_mapper = new ModelMapper();
        List<UserModel> chats_1 = source_user.getLast_chats();
        if (!chats_1.contains(target_user)){
            chats_1.add(target_user);
            source_user.setLast_chats(chats_1);
            user_repo.save(model_mapper.map(source_user, UserEntity.class));
        }
        List<UserModel> chats_2 = target_user.getLast_chats();
        if (!chats_2.contains(source_user)) {
            chats_2.add(source_user);
            target_user.setLast_chats(chats_2);
            user_repo.save(model_mapper.map(target_user, UserEntity.class));
        }
    }
    //endregion

    /**
     * This method finds a user by its ID.
     * @param id - ID of the user.
     */
    public UserModel find_user_by_id(Long id) {
        ModelMapper model_mapper = new ModelMapper();

       try {
           return model_mapper.map(user_repo.findById(id), UserModel.class);
       }
       catch (MappingException e) {
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
            set_user_to_premium(user);
        }
        else {
            throw new OurException("El user_id enviado es diferente al especificado en el token");
        }
    }
    public void set_premium_with_points(Long user_id) throws OurException {
        if(new TokenValidator().validate_id_with_token(user_id)) {
            UserModel user = find_user_by_id(user_id);
            if (user.getPoints()<1000){
                throw new OurException("No tienes suficientes puntos para ser premium");
            }else{
                user.setPoints(user.getPoints()-1000);
                set_user_to_premium(user);
            }
        }else {
            throw new OurException("El user_id enviado es diferente al especificado en el token");
        }
    }

    public void set_user_to_premium(UserModel user) throws OurException {
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


    /**
     * This method unsubscribe a user of the premium version
     * @param user_id: userId of the user that is being unsubscribed of the premium version
     */
    public void unsubscribe_premium(Long user_id) throws OurException {
        if(new TokenValidator().validate_id_with_token(user_id)) {
            UserModel user = find_user_by_id(user_id);
            if(user.isPremium()) {
                ModelMapper model_mapper = new ModelMapper();

                user.setPremium(false);
                user.setPremium_drop_date(LocalDate.now().toString());
                user_repo.save(model_mapper.map(user, UserEntity.class));
            }
            else {
                throw new OurException("El usuario no es premium");
            }
        }
        else {
            throw new OurException("El user_id enviado es diferente al especificado en el token");
        }
    }

    public ArrayList<ChargerModel> get_chargers_favorites(long user_id) throws OurException {
        if(new TokenValidator().validate_id_with_token(user_id)) {
            UserModel user = find_user_by_id(user_id);

            return (ArrayList<ChargerModel>) user.getFavorite_chargers();
        }
        else {
            throw new OurException("El user_id enviado es diferente al especificado en el token");
        }
    }

    public void add_favorite(Long user_id, Long charger_id) throws OurException {
        if(new TokenValidator().validate_id_with_token(user_id)) {
            ModelMapper model_mapper = new ModelMapper();
            UserModel user = find_user_by_id(user_id);
            ChargerModel charger = charger_service.find_charger_by_id(charger_id);

            user.getFavorite_chargers().add(charger);
            user_repo.save(model_mapper.map(user, UserEntity.class));
        }
        else {
            throw new OurException("El user_id enviado es diferente al especificado en el token");
        }
    }

    public void delete_favorite(Long user_id, Long charger_id) throws OurException {
        if(new TokenValidator().validate_id_with_token(user_id)) {
            ModelMapper model_mapper = new ModelMapper();
            UserModel user = find_user_by_id(user_id);
            ChargerModel charger = charger_service.find_charger_by_id(charger_id);

            user.getFavorite_chargers().remove(charger);
            user_repo.save(model_mapper.map(user, UserEntity.class));
        }
        else {
            throw new OurException("El user_id enviado es diferente al especificado en el token");
        }
    }

    public void add_points(Long user_id, long points){
        if(new TokenValidator().validate_id_with_token(user_id)) {
            ModelMapper model_mapper = new ModelMapper();
            UserModel user = find_user_by_id(user_id);

            user.setPoints(user.getPoints() + points);
            user_repo.save(model_mapper.map(user, UserEntity.class));
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
