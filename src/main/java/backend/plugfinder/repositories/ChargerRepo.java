package backend.plugfinder.repositories;

import backend.plugfinder.models.ChargerModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface ChargerRepo extends CrudRepository<ChargerModel, String> {
}