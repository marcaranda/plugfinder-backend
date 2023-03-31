package backend.plugfinder.services;
import backend.plugfinder.models.ChargerModel;
import backend.plugfinder.repositories.ChargerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ChargerService {
    @Autowired
    ChargerRepo chargerRepo;

    public ArrayList<ChargerModel> getChargers(){
        return (ArrayList<ChargerModel>) chargerRepo.findAll();
    }

    public ChargerModel saveCharger(ChargerModel chargerModel) {
        return chargerRepo.save(chargerModel);

    }

    public boolean deleteCharger(long id) {
        try{
            chargerRepo.deleteById(id);
            return true;
        }
        catch (Exception error){
            return false;
        }
    }
}
