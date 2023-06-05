package backend.plugfinder.repositories;

import backend.plugfinder.helpers.Zones;
import backend.plugfinder.repositories.entity.ChargerEntity;
import backend.plugfinder.repositories.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

/**
 * UserRepo
 * This class represents the repository for the user model. It extends the CrudRepository class because this class has all the basic operations
 * related with the DB.
 */
@Repository
public interface UserRepo extends CrudRepository<UserEntity, Long> {
    UserEntity findOneByEmail(String email);

    @Query("SELECT u FROM UserEntity u WHERE u.admin = false AND u.user_api = false ORDER BY u.points DESC LIMIT 50")
    ArrayList<UserEntity> findOrderRanking();
    //ArrayList<UserEntity> findOrderRanking(@Param("user_id") Long user_id);

    @Query("SELECT u FROM UserEntity u WHERE u.zone = :zone AND u.admin = false AND u.user_api = false ORDER BY u.points DESC LIMIT 50")
    ArrayList<UserEntity> findOrderRankingByZone(@Param("zone") Zones zone);

}
