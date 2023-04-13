package backend.plugfinder.repositories;

import backend.plugfinder.repositories.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * UserRepo
 * This class represents the repository for the user model. It extends the CrudRepository class because this class has all the basic operations
 * related with the DB.
 */
@Repository
public interface UserRepo extends CrudRepository<UserEntity, Long> {
    UserEntity findOneByEmail(String email);
}
