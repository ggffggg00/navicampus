package ru.borisof.navicampus.core.repo;

import org.springframework.data.repository.CrudRepository;
import ru.borisof.navicampus.core.dao.domain.FloorEntity;

public interface FloorRepo extends CrudRepository<FloorEntity, Integer> {
}
