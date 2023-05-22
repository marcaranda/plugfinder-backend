package backend.plugfinder.repositories;

import backend.plugfinder.repositories.entity.CommentEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface CommentRepo extends CrudRepository<CommentEntity, Long> {
    @Query("SELECT c FROM CommentEntity c WHERE c.charger.id_charger = :charger_id")
    public abstract ArrayList<CommentEntity> findChargerComments(@Param("charger_id") long charger_id);

    @Query("SELECT c FROM CommentEntity c WHERE c.user.user_id = :user_id")
    public abstract ArrayList<CommentEntity> findUserComments(@Param("user_id") long user_id);
}
