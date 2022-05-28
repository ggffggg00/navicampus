package ru.borisof.navicampus.core.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.borisof.navicampus.core.dao.domain.NavigationObject;
import ru.borisof.navicampus.core.service.NavigationObjectService;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/place")
public class NavigationObjectController {

    private final NavigationObjectService service;

    @PostMapping
    public ResponseEntity<NavigationObject> saveNavigationObject(@RequestBody NavigationObject navigationObject) {
        return ResponseEntity.ok(
                service.saveNavigationObject(navigationObject));
    }

    @GetMapping
    public ResponseEntity<Collection<NavigationObject>> getAllNavigationObjects() {
        return ResponseEntity.ok(service.getAllNavigationObjects());
    }

    @PutMapping
    public ResponseEntity<NavigationObject> updateNavigationObject(@RequestBody NavigationObject navigationObject) {
        return ResponseEntity.ok(service.updateNavigationObject(navigationObject));
    }

    @GetMapping("{id}")
    public ResponseEntity<NavigationObject> getNavigationObject(@RequestParam@PathVariable final long id) {
        return ResponseEntity.ok(service.getNavigationObjectById(id));
    }

    @GetMapping("find")
    public ResponseEntity<Collection<NavigationObject>> searchNavigationObject(@RequestParam("q") String query) {
        return ResponseEntity.ok(service.searchNavigationObjectByName(query));
    }


}
