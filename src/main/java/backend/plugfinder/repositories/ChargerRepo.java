package backend.plugfinder.repositories;

import backend.plugfinder.repositories.entity.ChargerEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;


@Repository
public interface ChargerRepo extends CrudRepository<ChargerEntity, Long> {
    @Query("SELECT c FROM ChargerEntity c WHERE c.is_public = :is_public")
    ArrayList<ChargerEntity> findAllByPublic(@Param("is_public") boolean is_public);
}