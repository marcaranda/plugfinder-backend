package backend.plugfinder.controllers;

import backend.plugfinder.controllers.dto.CarDto;
import backend.plugfinder.controllers.dto.CommentDto;
import backend.plugfinder.helpers.OurException;
import backend.plugfinder.services.CommentService;
import backend.plugfinder.services.models.CommentModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    CommentService comment_service;

    //region Get Methods
    @GetMapping("/charger/{charger_id}")
    @PreAuthorize("@securityService.not_userAPI()")
    public ArrayList<CommentDto> get_charger_comments(@PathVariable("charger_id") Long charger_id) throws OurException {
        ModelMapper model_mapper = new ModelMapper();
        return (ArrayList<CommentDto>) comment_service.get_charger_comments(charger_id).stream()
                .map(elementB -> model_mapper.map(elementB, CommentDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/user/{user_id}")
    @PreAuthorize("@securityService.not_userAPI()")
    public ArrayList<CommentDto> get_user_comments(@PathVariable("user_id") Long user_id) throws OurException {
        ModelMapper model_mapper = new ModelMapper();
        return (ArrayList<CommentDto>) comment_service.get_user_comments(user_id).stream()
                .map(elementB -> model_mapper.map(elementB, CommentDto.class))
                .collect(Collectors.toList());
    }
    //endregion

    //region Post Methods
    /** Saves a new comment
     * @param comment: Comment to be saved
     * @return: Saved comment
     */
    @PostMapping
    @PreAuthorize("@securityService.not_userAPI()")
    public CommentDto save_comment(@RequestBody CommentDto comment) throws OurException {
        ModelMapper model_mapper = new ModelMapper();
        return model_mapper.map(comment_service.save_comment(model_mapper.map(comment, CommentModel.class)), CommentDto.class);
    }
    //endregion

    //region Put Methods
    @PutMapping()
    @PreAuthorize("@securityService.not_userAPI()")
    public CommentDto update_comment(@RequestBody CommentDto updated_comment) throws OurException {
        ModelMapper model_mapper = new ModelMapper();
        return model_mapper.map(comment_service.update_comment(model_mapper.map(updated_comment, CommentModel.class)), CommentDto.class);
    }
    //endregion

    //region Delete Methods
    @DeleteMapping("/{comment_id}/delete")
    @PreAuthorize("@securityService.not_userAPI()")
    public ResponseEntity<String> delete_comment(@PathVariable("comment_id") Long comment_id) {
        return comment_service.delete_comment(comment_id);
    }
    //endregion
}
