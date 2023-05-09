package backend.plugfinder.controllers;

import backend.plugfinder.controllers.dto.LocationDto;
import backend.plugfinder.services.LocationService;
import backend.plugfinder.services.models.LocationModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/location")
public class LocationController {
    @Autowired
    LocationService location_service;

    //region Get Methods
    @GetMapping
    @PreAuthorize("@securityService.not_userAPI()")
    public ArrayList<LocationDto> get_locations(){
        ModelMapper model_mapper = new ModelMapper();
        ArrayList<LocationDto> locations = (ArrayList<LocationDto>) location_service.get_locations().stream()
                .map(elementB -> model_mapper.map(elementB, LocationDto.class))
                .collect(Collectors.toList());
        return locations;
    }

    @GetMapping(path = "/latitude/{latitude}/longitude/{longitude}")
    @PreAuthorize("@securityService.not_userAPI()")
    public LocationDto get_location(@PathVariable("latitude") double latitude, @PathVariable("longitude") double longitude){
        ModelMapper model_mapper = new ModelMapper();

        return model_mapper.map(location_service.get_location_by_id(latitude, longitude), LocationDto.class);
    }
    //endregion

    //region Post Methods
    @PostMapping
    @PreAuthorize("@securityService.not_userAPI()")
    public LocationDto save_location(@RequestBody LocationDto location_dto){
        ModelMapper model_mapper = new ModelMapper();

        return model_mapper.map(location_service.save_location(model_mapper.map(location_dto, LocationModel.class)), LocationDto.class);
    }
    //endregion
}
