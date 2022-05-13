package ru.borisof.navicampus.core.dao.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.borisof.navicampus.core.dao.domain.GraphNode;

public interface GraphNodeRepo extends JpaRepository<GraphNode, Long> {

    @Modifying
    @Query("delete from GraphNode gn where gn.building.id = :buildingId and gn.floor = :floor")
    void deleteByBuildingIdAndFloor(
            @Param("buildingId") int buildingId,
            @Param("floor") int floor
    );

}
