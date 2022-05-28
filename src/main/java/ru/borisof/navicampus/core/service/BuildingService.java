package ru.borisof.navicampus.core.service;

import ru.borisof.navicampus.core.dao.domain.Building;

import java.util.Collection;

public interface BuildingService {

    boolean isBuildingExists(int id);

    Building getBuilding(int id);

    Collection<Building> getBuildingList();

}
