package backend.plugfinder.repositories;

import backend.plugfinder.repositories.entity.ChargerEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ChargerRepo extends CrudRepository<ChargerEntity, Long> {
}