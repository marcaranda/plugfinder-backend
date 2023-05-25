package backend.plugfinder.services;

import backend.plugfinder.helpers.OurException;
import backend.plugfinder.repositories.CommentRepo;
import backend.plugfinder.repositories.entity.CommentEntity;
import backend.plugfinder.services.models.CommentModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    CommentRepo comment_repo;

    @Autowired
    ChargerService charger_service;

    @Autowired
    UserService user_service;


    /** Returns all the comments of a charger
     * @param charger_id: Id of the charger
     * @return List of comments
     * @throws OurException: If the charger does not exist
     */
    public ArrayList<CommentModel> get_charger_comments(Long charger_id) throws OurException {
        ModelMapper model_mapper = new ModelMapper();
        ArrayList<CommentModel> comments = new ArrayList<>();
        if(charger_id != null && charger_service.find_charger_by_id(charger_id) != null) {
            comment_repo.findChargerComments(charger_id).forEach(elementB -> comments.add(model_mapper.map(elementB, CommentModel.class)));
            return comments;
        }
        else {
            throw new OurException ("The charger not exists");
        }
    }

    /** Returns all the comments of a user
     * @param user_id: Id of the user
     * @return List of comments
     * @throws OurException: If the user does not exist
     */
    public ArrayList<CommentModel> get_user_comments(Long user_id) throws OurException {
        ModelMapper model_mapper = new ModelMapper();
        ArrayList<CommentModel> comments = new ArrayList<>();
        if(user_id != null && user_service.find_user_by_id(user_id) != null) {
            comment_repo.findUserComments(user_id).forEach(elementB -> comments.add(model_mapper.map(elementB, CommentModel.class)));
            return comments;
        }
        else {
            throw new OurException ("The user not exists");
        }
    }

    /**
     * Saves a new comment
     * @param comment_model: Comment to be saved
     * @return Saved comment
     */
    public CommentModel save_comment(CommentModel comment_model) throws OurException {
        ModelMapper model_mapper = new ModelMapper();
        try {
            return model_mapper.map(comment_repo.save(model_mapper.map(comment_model, CommentEntity.class)), CommentModel.class);
        }
        catch (Exception e){
            throw new OurException("Error saving the comment");
        }
    }

    /**
     * Updates a comment
     * @param comment_model: Comment to be updated
     * @return Updated comment
     */
    public CommentModel update_comment(CommentModel comment_model) throws OurException {
        ModelMapper model_mapper = new ModelMapper();
        try {
            //As we are giving the comment id it will make an update not a save
            return model_mapper.map(comment_repo.save(model_mapper.map(comment_model, CommentEntity.class)), CommentModel.class);
        }
        catch (Exception e){
            throw new OurException("Error updating the comment");
        }
    }

    /**
     * Deletes a comment
     * @param comment_id: Id of the comment to be deleted
     * @return Response entity with the result of the operation
     */
    public ResponseEntity<String> delete_comment(Long comment_id) {
        try {
            if (comment_repo.findById(comment_id).isPresent()) {
                comment_repo.deleteById(comment_id);
                return ResponseEntity.ok("Comment deleted");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comment not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }
}
