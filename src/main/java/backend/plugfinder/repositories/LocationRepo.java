package backend.plugfinder.repositories;

import backend.plugfinder.helpers.LocationId;
import backend.plugfinder.repositories.entity.LocationEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepo extends CrudRepository<LocationEntity, LocationId> {
}
