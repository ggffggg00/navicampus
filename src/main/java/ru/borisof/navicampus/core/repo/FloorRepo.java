package ru.borisof.navicampus.core.repo;

import org.springframework.data.repository.CrudRepository;
import ru.borisof.navicampus.core.dao.domain.FloorEntity;

import java.util.Collection;

public interface FloorRepo extends CrudRepository<FloorEntity, Integer> {

    Collection<FloorEntity> findAllByBuildingId(final Integer building_id);

}
