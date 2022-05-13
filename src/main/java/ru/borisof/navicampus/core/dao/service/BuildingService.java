package ru.borisof.navicampus.core.dao.service;

import ru.borisof.navicampus.core.dao.domain.Building;

public interface BuildingService {

    boolean isBuildingExists(int id);

    Building getBuilding(int id);

}
