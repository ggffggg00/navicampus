package ru.borisof.navicampus.core.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.borisof.navicampus.core.dao.domain.NavigationObject;

import java.util.Collection;

public interface NavigationObjectRepo extends JpaRepository<NavigationObject, Long> {

    Collection<NavigationObject> findByNameContaining(final String name);

}
