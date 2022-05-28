package ru.borisof.navicampus.core.graph.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.borisof.navicampus.core.graph.domain.WaypointEntity;
import ru.borisof.navicampus.core.graph.jdbc.QueryExecutor;
import ru.borisof.navicampus.core.graph.jdbc.query.CreateEdgeQuery;
import ru.borisof.navicampus.core.graph.jdbc.query.FindShortestPathQuery;
import ru.borisof.navicampus.core.graph.jdbc.query.GetAllRoutesAtFloorQuery;
import ru.borisof.navicampus.core.graph.repo.WaypointRepository;
import ru.borisof.navicampus.core.service.NavigationObjectService;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static ru.borisof.navicampus.core.common.util.Common.calculateDistanceBetweenPoints;

@Service
@RequiredArgsConstructor
public class GraphService {

    private final WaypointRepository repository;
    private final QueryExecutor queryExecutor;
    private final NavigationObjectService placeService;

    public void findShortestPath(long startPlaceId, long endPlaceId) {
        var a = placeService.getNavigationObjectById(startPlaceId);
        var b = placeService.getNavigationObjectById(endPlaceId);

        queryExecutor.executeStm(FindShortestPathQuery.builder()
                .startId(a.getGraphNodeId())
                .endId(b.getGraphNodeId())
                .build());

    }

    public void updateGraph(int buildingId, int floor, GraphInfo graphInfo) {
        deleteAllFloorForGraph(buildingId, floor);
        var waypoints = graphInfo.getWaypoints();
        var routes = graphInfo.getRoutes();

        var waypointsSavedMap = waypoints.stream()
                .map((el) -> Map.entry(el.getId(), repository.save(el.toEntityWithoutId())))
                .peek((el) -> {
                    if (el.getValue().getNavigationObjectId() > 0)
                        placeService.getNavigationObjectById(el.getValue().getNavigationObjectId())
                            .setGraphNodeId(el.getValue().getId());
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        routes.stream()
                .map((el -> {
                    var startWaypoint = waypointsSavedMap.get(el.getNodeIdStart());
                    var endWaypoint = waypointsSavedMap.get(el.getNodeIdEnd());

                    return Route.builder()
                            .nodeIdStart(startWaypoint.getId())
                            .nodeIdEnd(endWaypoint.getId())
                            .cost(calculateDistanceBetweenPoints(
                                    startWaypoint.getLat(), startWaypoint.getLng(),
                                    endWaypoint.getLat(), endWaypoint.getLng()
                            ))
                            .build();
                }))
                .forEach(this::saveRoute);

    }

    public GraphInfo getGraphForBuildingAndFloor(int buildingId, int floor) {
        var nodes = repository.findByBuildingIdAndFloor(buildingId, floor);

        var routes = new HashSet<>(getFloorRoutes(buildingId, floor));
        var waypoints = nodes.stream()
                .map(Waypoint::fromEntity).toList();

        return GraphInfo.builder()
                .buildingId(buildingId)
                .floor(floor)
                .waypoints(waypoints)
                .routes(routes)
                .build();

    }

    private Collection<Route> getFloorRoutes(int buildingId, int floor) {
        return queryExecutor.executeStm(GetAllRoutesAtFloorQuery.builder()
                .buildingId(buildingId)
                .floor(floor)
                .build());
    }

    private void saveRoute(final Route route) {
        queryExecutor.executeStm(CreateEdgeQuery.builder()
                .startId(route.getNodeIdStart())
                .endId((route.getNodeIdEnd()))
                .cost(route.getCost())
                .build());
    }

    private void deleteAllFloorForGraph(int buildingId, int floor) {
        repository.deleteAllByBuildingIdAndFloor(buildingId, floor);
    }


    @Data
    @Builder
    public static class GraphInfo {
        private int buildingId;
        private int floor;
        private Collection<Waypoint> waypoints;
        private Collection<Route> routes;
    }

    @Data
    @Builder
    public static class Waypoint {
        private long id;
        private int buildingId;
        private double lat;
        private double lng;
        private int floor;
        private long navigationObjectId;

        public static Waypoint fromEntity(WaypointEntity entity) {
            return Waypoint.builder()
                    .buildingId(entity.getBuildingId())
                    .id(entity.getId())
                    .lat(entity.getLat())
                    .lng(entity.getLng())
                    .floor(entity.getFloor())
                    .navigationObjectId(entity.getNavigationObjectId())
                    .build();
        }

        public WaypointEntity toEntityWithoutId() {
            return WaypointEntity.builder()
                    .buildingId(buildingId)
                    .lat(lat)
                    .lng(lng)
                    .floor(floor)
                    .navigationObjectId(navigationObjectId)
                    .build();
        }
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class Route {
        private long nodeIdStart;
        private long nodeIdEnd;
        private double cost;

        //Переопределяем Equals и hashcode, так, чтобы коллекции считали, что объекты с инвертированными ID одинаковы
        // и не добавляла их в коллекцию
        @Override
        public boolean equals(final Object obj) {
            return obj instanceof Route && (
                    ((Route) obj).getNodeIdStart() == this.getNodeIdStart() && ((Route) obj).getNodeIdEnd() == this.getNodeIdEnd() ||
                    ((Route) obj).getNodeIdStart() == this.getNodeIdEnd() && ((Route) obj).getNodeIdEnd() == this.getNodeIdStart());
        }

        @Override
        public int hashCode() {
            return Objects.hash(nodeIdStart, nodeIdEnd);
        }
    }


}
