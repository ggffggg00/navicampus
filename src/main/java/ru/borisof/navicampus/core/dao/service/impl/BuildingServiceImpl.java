package ru.borisof.navicampus.core.dao.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.borisof.navicampus.core.dao.domain.Building;
import ru.borisof.navicampus.core.common.exception.NotFoundException;
import ru.borisof.navicampus.core.dao.repo.BuildingRepo;
import ru.borisof.navicampus.core.dao.service.BuildingService;

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
}
