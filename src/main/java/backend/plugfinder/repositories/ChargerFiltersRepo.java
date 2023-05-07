package backend.plugfinder.repositories;

import backend.plugfinder.repositories.entity.ChargerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ChargerFiltersRepo extends JpaRepository<ChargerEntity, Long>, JpaSpecificationExecutor<ChargerEntity> {}