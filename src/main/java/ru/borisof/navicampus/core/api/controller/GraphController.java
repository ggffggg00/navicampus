package ru.borisof.navicampus.core.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.borisof.navicampus.core.graph.model.PathEntry;
import ru.borisof.navicampus.core.graph.service.GraphService;

import java.util.Collection;

@RestController
@RequestMapping("api/graph")
@RequiredArgsConstructor
public class GraphController {

    private final GraphService graphService;

    @PostMapping
    public ResponseEntity updateGraph(@RequestBody final GraphService.GraphInfo req) {
        graphService.updateGraph(req.getBuildingId(), req.getFloor(), req);
        return ResponseEntity.ok(null);
    }

    @GetMapping("{buildingId}/{floor}")
    public ResponseEntity<GraphService.GraphInfo> getGraph(
            @PathVariable final int buildingId,
            @PathVariable final int floor) {

        return ResponseEntity.ok(
                graphService.getGraphForBuildingAndFloor(buildingId, floor));
    }

    @GetMapping("findPath/{start}/{end}")
    public ResponseEntity<Collection<PathEntry>> findShortestPath(
            @PathVariable final long start,
            @PathVariable final long end) {

        return ResponseEntity.ok(
                graphService.findShortestPath(start, end));
    }

    @GetMapping("connectBuildings/{start}/{end}")
    public ResponseEntity connectBuildings(@PathVariable final int end, @PathVariable final int start) {
        graphService.connectBuildings(start, end);
        return ResponseEntity.of(null);
    }

}
