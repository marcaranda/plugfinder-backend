package backend.plugfinder.repositories;

import backend.plugfinder.repositories.entity.ChargerTypeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChargerTypeRepo extends CrudRepository<ChargerTypeEntity, Long> {
}
