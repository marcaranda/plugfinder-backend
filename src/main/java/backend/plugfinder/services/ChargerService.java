package backend.plugfinder.services;
import backend.plugfinder.repositories.entity.ChargerEntity;
import backend.plugfinder.services.models.ChargerModel;
import backend.plugfinder.repositories.ChargerRepo;
import org.modelmapper.ModelMapper;
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

    public ChargerModel save_charger(ChargerModel chargerModel) {
        ModelMapper model_mapper = new ModelMapper();
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
