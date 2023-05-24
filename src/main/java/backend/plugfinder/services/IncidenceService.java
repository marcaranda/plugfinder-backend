package backend.plugfinder.services;

import backend.plugfinder.helpers.OurException;
import backend.plugfinder.repositories.IncidenceRepo;
import backend.plugfinder.repositories.entity.IncidenceEntity;
import backend.plugfinder.services.models.IncidenceModel;
import com.amazonaws.Response;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
public class IncidenceService {
    @Autowired
    IncidenceRepo incidence_repo;

    /** Returns all the incidences of a charger
     * @param charger_id: Id of the charger
     * @return List of incidences
     * @throws OurException: If the charger does not exist
     */
    public ArrayList<IncidenceModel> get_charger_incidences(Long charger_id) throws OurException {
        ModelMapper model_mapper = new ModelMapper();
        ArrayList<IncidenceModel> incidences = new ArrayList<>();
        if(charger_id != null) {
            incidence_repo.findChargerIncidences(charger_id).forEach(elementB -> incidences.add(model_mapper.map(elementB, IncidenceModel.class)));
            return incidences;
        }
        else {
            throw new OurException ("The charger not exists");
        }
    }

    /** Returns all the incidences of a user
     * @param user_id: Id of the user
     * @return List of incidences
     * @throws OurException: If the user does not exist
     */
    public ArrayList<IncidenceModel> get_user_incidences(Long user_id) throws OurException {
        ModelMapper model_mapper = new ModelMapper();
        ArrayList<IncidenceModel> incidences = new ArrayList<>();
        if(user_id != null) {
            incidence_repo.findUserIncidences(user_id).forEach(elementB -> incidences.add(model_mapper.map(elementB, IncidenceModel.class)));
            return incidences;
        }
        else {
            throw new OurException ("The user not exists");
        }
    }

    /** Saves an incidence
     * @param incidence: Incidence to save
     * @return Incidence saved
     * @throws OurException: If the incidence could not be saved
     */
    public IncidenceModel save_incidence(IncidenceModel incidence) throws OurException {
        ModelMapper model_mapper = new ModelMapper();
        try {
            return model_mapper.map(incidence_repo.save(model_mapper.map(incidence, IncidenceEntity.class)), IncidenceModel.class);
        }
        catch (Exception e){
            throw new OurException("Error saving the incidence");
        }
    }

    /** Updates an incidence
     * @param incidence: Incidence to update
     * @return Incidence updated
     * @throws OurException: If the incidence could not be updated
     */
    public IncidenceModel update_incidence(IncidenceModel incidence) throws OurException {
        ModelMapper model_mapper = new ModelMapper();
        try {
            //As we are giving the comment id it will make an update not a save
            return model_mapper.map(incidence_repo.save(model_mapper.map(incidence, IncidenceEntity.class)), IncidenceModel.class);
        }
        catch (Exception e){
            throw new OurException("Error saving the incidence");
        }
    }

    /** Deletes an incidence
     * @param incidence_id: Id of the incidence to delete
     * @return Response
     * @throws OurException: If the incidence does not exist
     */
    public ResponseEntity<String> delete_incidence(Long incidence_id) {
        try {
            if (incidence_repo.findById(incidence_id).isPresent()) {
                incidence_repo.deleteById(incidence_id);
                return ResponseEntity.ok("Incidence deleted");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Incidence not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }
}
