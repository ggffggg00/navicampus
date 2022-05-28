package ru.borisof.navicampus.core.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.borisof.navicampus.core.dao.domain.Building;

public interface BuildingRepo extends JpaRepository<Building, Integer> {
}
