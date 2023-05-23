package backend.plugfinder.controllers;

import backend.plugfinder.controllers.dto.IncidenceDto;
import backend.plugfinder.helpers.OurException;
import backend.plugfinder.services.IncidenceService;
import backend.plugfinder.services.models.IncidenceModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/incidence")
public class IncidenceController {
    @Autowired
    IncidenceService incidence_service;

    //region Get methods
    @GetMapping("/charger/{charger_id}")
    @PreAuthorize("@securityService.not_userAPI()")
    public ArrayList<IncidenceDto> get_charger_incidences(@PathVariable("charger_id") Long charger_id) throws OurException {
        ModelMapper model_mapper = new ModelMapper();
        return (ArrayList<IncidenceDto>) incidence_service.get_charger_incidences(charger_id).stream()
                .map(elementB -> model_mapper.map(elementB, IncidenceDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/user/{user_id}")
    @PreAuthorize("@securityService.not_userAPI()")
    public ArrayList<IncidenceDto> get_user_incidences(@PathVariable("user_id") Long user_id) throws OurException {
        ModelMapper model_mapper = new ModelMapper();
        return (ArrayList<IncidenceDto>) incidence_service.get_user_incidences(user_id).stream()
                .map(elementB -> model_mapper.map(elementB, IncidenceDto.class))
                .collect(Collectors.toList());
    }
    //endregion

    //region Post methods
    @PostMapping
    @PreAuthorize("@securityService.not_userAPI()")
    public IncidenceDto save_incidence(@RequestBody IncidenceDto incidence) throws OurException {
        ModelMapper model_mapper = new ModelMapper();
        return model_mapper.map(incidence_service.save_incidence(model_mapper.map(incidence, IncidenceModel.class)), IncidenceDto.class);
    }
    //endregion

    //region Put methods
    @PutMapping()
    @PreAuthorize("@securityService.not_userAPI()")
    public IncidenceDto update_incidence(@RequestBody IncidenceDto updated_incidence) throws OurException {
        ModelMapper model_mapper = new ModelMapper();
        return model_mapper.map(incidence_service.update_incidence(model_mapper.map(updated_incidence, IncidenceModel.class)), IncidenceDto.class);
    }
    //endregion

    //region Delete methods
    @DeleteMapping("/{incidence_id}")
    @PreAuthorize("@securityService.not_userAPI()")
    public ResponseEntity<String> delete_incidence(@PathVariable("incidence_id") Long incidence_id) throws OurException {
        return incidence_service.delete_incidence(incidence_id);
    }
    //endregion
}
