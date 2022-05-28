package ru.borisof.navicampus.core.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.borisof.navicampus.core.dao.domain.Building;
import ru.borisof.navicampus.core.service.BuildingService;

import java.util.Collection;

@RestController
@RequestMapping("api/building")
@RequiredArgsConstructor
public class BuildingController {

    private final BuildingService buildingService;

    @GetMapping
    public ResponseEntity<Collection<Building>> getListBuildings() {
        return ResponseEntity.ok(buildingService.getBuildingList());
    }

}
