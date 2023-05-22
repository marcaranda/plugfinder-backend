package backend.plugfinder.repositories;

import backend.plugfinder.repositories.entity.CommentEntity;
import backend.plugfinder.repositories.entity.IncidenceEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface IncidenceRepo extends CrudRepository<IncidenceEntity, Long> {
    @Query("SELECT i FROM IncidenceEntity i WHERE i.charger.id_charger = :charger_id")
    public abstract ArrayList<IncidenceEntity> findChargerIncidences(@Param("charger_id") long charger_id);

    @Query("SELECT i FROM IncidenceEntity i WHERE i.user.user_id = :user_id")
    public abstract ArrayList<IncidenceEntity> findUserIncidences(@Param("user_id") long user_id);
}
