package backend.plugfinder.services;
import backend.plugfinder.models.ChargerModel;
import backend.plugfinder.repositories.ChargerRepo;

import java.util.ArrayList;

public class ChargerService {
    ChargerRepo chargerRepo;

    public ArrayList<ChargerModel> getChargers(){
        return (ArrayList<ChargerModel>) chargerRepo.findAll();
    }

    public ChargerModel saveCharger(ChargerModel chargerModel) {
        return chargerRepo.save(chargerModel);

    }

    public boolean deleteCharger(String id) {
        try{
            chargerRepo.deleteById(id);
            return true;
        }
        catch (Exception error){
            return false;
        }
    }
}
