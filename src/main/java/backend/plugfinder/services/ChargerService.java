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
        //Indicamos que es un cargador privado
        chargerModel.setIs_public(false);
        //Asignamos el usuario como propietario
        chargerModel.setOwner_user(user);
        //Localizacion? -> Si frontend puede mandar la localizacion en el json no hace falta, sino tenemos que pedir que nos mande como parametros
        //la longitud i la latitud y crear un objeto LocationEntity y asignarlo al cargador
        return model_mapper.map(charger_repo.save(model_mapper.map(chargerModel, ChargerEntity.class)), ChargerModel.class);
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



}
