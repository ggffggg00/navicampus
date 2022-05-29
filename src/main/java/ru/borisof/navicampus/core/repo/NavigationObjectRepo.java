package ru.borisof.navicampus.core.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.borisof.navicampus.core.dao.domain.NavigationObject;

import java.util.Collection;

public interface NavigationObjectRepo extends JpaRepository<NavigationObject, Long> {

    Collection<NavigationObject> findByNameContainingIgnoreCase(final String name);

    @Query("select p from NavigationObject  p where p.floor.id = :floorId or (p.building.id = :buildingId and p"
           + ".isLadder = true)")
    Collection<NavigationObject> findAllByFloorIdWithLadders(@Param("floorId") final int floorId,
                                                             @Param("buildingId") final int buildingId);

}
