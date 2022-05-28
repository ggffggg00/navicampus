package ru.borisof.navicampus.core.graph.repo;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import ru.borisof.navicampus.core.graph.domain.WaypointEntity;

import java.util.Collection;
import java.util.Optional;

public interface WaypointRepository extends Neo4jRepository<WaypointEntity, Long> {

    void deleteAllByBuildingIdAndFloor(final int buildingId, final int floor);

    Collection<WaypointEntity> findByBuildingIdAndFloor(final int buildingId, final int floor);

    Optional<WaypointEntity> findByNavigationObjectId(final long navigationObjectId);

    Collection<WaypointEntity> findAllByNavigationObjectId(final long navigationObjectId);

}
