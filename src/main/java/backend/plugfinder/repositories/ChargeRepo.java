package backend.plugfinder.repositories;

import backend.plugfinder.models.ChargeModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface ChargeRepo extends CrudRepository<ChargeModel, Long>  {

    @Query("SELECT cm FROM ChargeModel cm WHERE cm.car.id.id = :user_id")
    ArrayList<ChargeModel> get_charges_by_user_id(@Param("user_id") long user_id);
}
