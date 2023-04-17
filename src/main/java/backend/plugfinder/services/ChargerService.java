package backend.plugfinder.services;
import backend.plugfinder.repositories.entity.ChargerEntity;
import backend.plugfinder.repositories.entity.UserEntity;
import backend.plugfinder.services.models.ChargerModel;
import backend.plugfinder.repositories.ChargerRepo;
import backend.plugfinder.services.models.UserModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ChargerService {
    @Autowired
    ChargerRepo charger_repo;

    public ArrayList<ChargerModel> get_chargers(){
        ModelMapper model_mapper = new ModelMapper();
        ArrayList<ChargerModel> chargers = new ArrayList<>();
        charger_repo.findAll().forEach(elementB -> chargers.add(model_mapper.map(elementB, ChargerModel.class)));
        return chargers;
    }

    /** Get all private chargers
     * @return: ArrayList with the private chargers
     */
    public ArrayList<ChargerModel> get_private_chargers() {
        ModelMapper model_mapper = new ModelMapper();
        ArrayList<ChargerModel> chargers = new ArrayList<>();
        charger_repo.findAll().forEach(elementB -> {
            if(!elementB.isIs_public()){
                chargers.add(model_mapper.map(elementB, ChargerModel.class));
            }
        });
        return chargers;
    }

    /**
     * Get all chargers by location
     * @param latitude: Latitude of the location
     * @param longitude: Longitude of the location
     * @return: ArrayList with the chargers
     */
    public ArrayList<ChargerModel> get_chargers_by_location(double latitude, double longitude) {
        ModelMapper model_mapper = new ModelMapper();
        ArrayList<ChargerModel> chargers = new ArrayList<>();
        charger_repo.findAll().forEach(elementB -> {
            //Si la distancia entre el cargador i el punt seleccionat es menor a 5km afegira el carregador a la llista
            if(Haversine(latitude, longitude, elementB.getLatitude(), elementB.getLongitude(), 5)){
                chargers.add(model_mapper.map(elementB, ChargerModel.class));
            }
        });
        return chargers;
    }

    public ChargerModel save_charger(ChargerModel chargerModel) {
        ModelMapper model_mapper = new ModelMapper();
        return model_mapper.map(charger_repo.save(model_mapper.map(chargerModel, ChargerEntity.class)), ChargerModel.class);

    }

    /** Create a new private charger
     * @param user: Owner of the charger
     * @param chargerModel: Charger to save
     * @return
     */
    public ChargerModel save_private_charger(UserModel user, ChargerModel chargerModel) {
        ModelMapper model_mapper = new ModelMapper();
        ChargerModel new_charger = model_mapper.map(charger_repo.save(model_mapper.map(chargerModel, ChargerEntity.class)), ChargerModel.class);
        //Indicamos que es un cargador privado
        new_charger.setIs_public(false);
        //Asignamos el usuario como propietario
        new_charger.setOwner_user(user);
        //Localizacion? -> Si frontend puede mandar la localizacion en el json no hace falta, sino tenemos que pedir que nos mande como parametros
        //la longitud i la latitud y crear un objeto LocationEntity y asignarlo al cargador
        return new_charger;
    }

    public boolean delete_charger(long id) {
        try{
            charger_repo.deleteById(id);
            return true;
        }
        catch (Exception error){
            return false;
        }
    }

    //region Metodos Privados
    /** Check if the distance between the charger and the point is less than the max distance
     * @param latitude: Latitude of the point
     * @param longitude: Longitude of the point
     * @param latitude_charger: Latitude of the charger
     * @param longitude_charger: Longitude of the charger
     * @param max_distance: Max distance between the charger and the point
     * @return: True if the distance between the charger and the point is less than the max distance
     */
    private boolean Haversine(double latitude, double longitude, double latitude_charger, double longitude_charger, double max_distance) {
        // Radi de la terra en km
        double earth_radius = 6371.0; //(6378 si vui agafar l'equatorial)

        //Caluclem la distància entre l'ubicació especificada i el carregador
        double distance = earth_radius * Math.acos(Math.cos(Math.toRadians(latitude)) * Math.cos(Math.toRadians(latitude_charger)) * Math.cos(Math.toRadians(longitude_charger)
                - Math.toRadians(longitude)) + Math.sin(Math.toRadians(latitude)) * Math.sin(Math.toRadians(latitude_charger)));

        if(distance <= max_distance){
            return true;
        }
        return false;
    }
    //endregion


}
