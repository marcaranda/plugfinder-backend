package backend.plugfinder.services;

import backend.plugfinder.helpers.LocationId;
import backend.plugfinder.repositories.LocationRepo;
import backend.plugfinder.repositories.entity.LocationEntity;
import backend.plugfinder.services.models.LocationModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class LocationService {
    @Autowired
    LocationRepo location_repo;

    public ArrayList<LocationModel> get_locations() {
        ModelMapper model_mapper = new ModelMapper();
        ArrayList<LocationModel> locations = new ArrayList<>();

        location_repo.findAll().forEach(elementB -> locations.add(model_mapper.map(elementB, LocationModel.class)));
        return locations;
    }

    public LocationModel get_location_by_id(double latitude, double longitude) {
        ModelMapper model_mapper = new ModelMapper();

        LocationId id = new LocationId(latitude, longitude);

        return model_mapper.map(location_repo.findById(id).get(), LocationModel.class);
    }

    public LocationModel save_location(LocationModel location_model) {
        ModelMapper model_mapper = new ModelMapper();
        return model_mapper.map(location_repo.save(model_mapper.map(location_model, LocationEntity.class)), LocationModel.class);
    }

    //region Public Methods

    //endregion
}
