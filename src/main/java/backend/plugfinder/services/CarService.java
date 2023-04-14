package backend.plugfinder.services;

import backend.plugfinder.controllers.dto.CarDto;
import backend.plugfinder.helpers.OurException;
import backend.plugfinder.helpers.TokenValidator;
import backend.plugfinder.repositories.entity.CarEntity;
import backend.plugfinder.services.models.CarModel;
import backend.plugfinder.repositories.CarRepo;
import backend.plugfinder.services.models.UserModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class CarService {
    @Autowired
    CarRepo car_repo;
    @Autowired
    UserService user_service;

    //region Public Methods
    public ArrayList<CarModel> get_cars(Long user_id) throws OurException {
        ModelMapper model_mapper = new ModelMapper();
        ArrayList<CarModel> cars = new ArrayList<>();

        if (user_id != null) {
            if (new TokenValidator().validate_id_with_token(user_id)) {
                car_repo.findCarModelsById_Id(user_id).forEach(elementB -> cars.add(model_mapper.map(elementB, CarModel.class)));
            }
            else {
                throw new OurException("El user_id enviado es diferente al especificado en el token");
            }
        }
        else car_repo.findAll().forEach(elementB -> cars.add(model_mapper.map(elementB, CarModel.class)));
        return cars;
    }

    public CarModel save_car(CarModel car_model) throws SQLException {
        ModelMapper model_mapper = new ModelMapper();

        /* Comprobación validez matrícula */
        if (!validate_license(car_model.getId().getLicense())){
            throw new SQLException("La matrícula no es válida");
        }

        UserModel user_model = user_service.find_user_by_id(car_model.getId().getId());
        car_model.setUser_model(user_model);

        return model_mapper.map(car_repo.save(model_mapper.map(car_model, CarEntity.class)), CarModel.class);
    }

    public String delete_car(String license, long user_id) throws OurException {
        if (new TokenValidator().validate_id_with_token(user_id)) {
            ModelMapper model_mapper = new ModelMapper();

            CarModel car = model_mapper.map(car_repo.findCarModelById_LicenseAndId_Id(license, user_id), CarModel.class);
            if (car != null) {
                car.setDeleted(true);
                car.setUser_model(null);
                car_repo.save(model_mapper.map(car, CarEntity.class));
                return "Se elimino correctamente el coche con matricula " + license;
            } else {
                return "No se ha podido eliminar el coche con matricula " + license;
            }        }
        else {
            throw new OurException("El user_id enviado es diferente al especificado en el token");
        }
    }

    public CarModel get_car_by_id(String license, long user_id) throws OurException {
        if (new TokenValidator().validate_id_with_token(user_id)) {
            ModelMapper model_mapper = new ModelMapper();

            return model_mapper.map(car_repo.findCarModelById_LicenseAndId_Id(license, user_id), CarModel.class);        }
        else {
            throw new OurException("El user_id enviado es diferente al especificado en el token");
        }
    }

    public ArrayList<CarModel> get_cars_by_user_id(long user_id){
        ModelMapper model_mapper = new ModelMapper();
        ArrayList<CarModel> cars = new ArrayList<>();
        car_repo.findCarModelsById_Id(user_id).forEach(elementB -> cars.add(model_mapper.map(elementB, CarModel.class)));
        return cars;
    }
    //endregion

    //region Private Methods
    private boolean validate_license(String license){
        String patron = "^\\d{4}[BCDFGHJKLMNÑPQRSTVWXYZ]{3}$";
        Pattern pattern = Pattern.compile(patron);
        Matcher matcher = pattern.matcher(license);
        return matcher.matches();
    }
    //endregion
}
