package backend.plugfinder.repositories;

import backend.plugfinder.repositories.entity.ReservationEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepo extends CrudRepository<ReservationEntity, Long> {

}
