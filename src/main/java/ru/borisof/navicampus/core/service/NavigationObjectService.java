package ru.borisof.navicampus.core.service;

import ru.borisof.navicampus.core.dao.domain.NavigationObject;

import java.util.Collection;

public interface NavigationObjectService {

    NavigationObject saveNavigationObject(NavigationObject navigationObject);

    NavigationObject updateNavigationObject(NavigationObject navigationObject);

    Collection<NavigationObject> getAllNavigationObjects();

    Collection<NavigationObject> searchNavigationObjectByName(String query);

    NavigationObject getNavigationObjectById(long id);


}
