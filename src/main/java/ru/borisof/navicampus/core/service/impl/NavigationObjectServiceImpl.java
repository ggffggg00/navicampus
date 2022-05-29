package ru.borisof.navicampus.core.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.comparator.Comparators;
import ru.borisof.navicampus.core.common.exception.NotFoundException;
import ru.borisof.navicampus.core.dao.domain.NavigationObject;
import ru.borisof.navicampus.core.repo.NavigationObjectRepo;
import ru.borisof.navicampus.core.service.NavigationObjectService;

import java.util.Collection;
import java.util.Comparator;

@Service
@RequiredArgsConstructor
public class NavigationObjectServiceImpl implements NavigationObjectService {

    private final NavigationObjectRepo repo;

    private static final Comparator<NavigationObject> PLACE_COMPARATOR =
            Comparator.comparing(NavigationObject::getName);


    @Override
    public NavigationObject saveNavigationObject(final NavigationObject navigationObject) {
        return repo.save(navigationObject);
    }

    @Override
    public NavigationObject updateNavigationObject(final NavigationObject navigationObject) {
        if (navigationObject == null || navigationObject.getId() == null)
            throw new RuntimeException("Id cannot be null");

        var no = repo.findById(navigationObject.getId());

        if (no.isEmpty())
            throw new RuntimeException("Navigation object not found");

        return repo.save(navigationObject);
    }

    @Override
    public Collection<NavigationObject> getAllNavigationObjects() {
        return repo.findAll();
    }

    @Override
    public Collection<NavigationObject> getPlacesAtFloor(final int floorId, int buildingId) {
        return repo.findAllByFloorIdWithLadders(floorId, buildingId).stream().sorted(PLACE_COMPARATOR).toList();
    }

    @Override
    public Collection<NavigationObject> searchNavigationObjectByName(final String query) {
        return repo.findByNameContainingIgnoreCase(query);
    }

    @Override
    public NavigationObject getNavigationObjectById(final long id) {
            return repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Место не найдено"));
    }
}
