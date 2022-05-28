package ru.borisof.navicampus.core.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.borisof.navicampus.core.dao.domain.Building;
import ru.borisof.navicampus.core.common.exception.NotFoundException;
import ru.borisof.navicampus.core.repo.BuildingRepo;
import ru.borisof.navicampus.core.service.BuildingService;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class BuildingServiceImpl implements BuildingService {

    private final BuildingRepo buildingRepo;

    @Override
    public boolean isBuildingExists(final int id) {
        return buildingRepo.existsById(id);
    }

    @Override
    public Building getBuilding(int id) {
        return buildingRepo.findById(id).orElseThrow(
                () -> new NotFoundException("Здание не найдено"));
    }

    @Override
    public Collection<Building> getBuildingList() {
        return buildingRepo.findAll();
    }

}
