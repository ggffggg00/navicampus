package ru.borisof.navicampus.core.graph.jdbc.query;

import lombok.Builder;
import ru.borisof.navicampus.core.graph.jdbc.Neo4jNativeQuery;
import ru.borisof.navicampus.core.graph.service.GraphService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;

@Builder
public class FindShortestPathQuery extends Neo4jNativeQuery<Collection<GraphService.Waypoint>> {

    private long startId;
    private long endId;

    @Override
    protected String createStatement() {
        return "MATCH (source:Waypoint), (target:Waypoint)\n"
               + "WHERE id(source) = " + startId + " and id(target) = " + endId + "\n"
               + "CALL gds.shortestPath.dijkstra.stream('routeGraph', {\n"
               + "    sourceNode: source,\n"
               + "    targetNode: target,\n"
               + "    relationshipWeightProperty: 'cost'\n"
               + "})\n"
               + "YIELD index, sourceNode, targetNode, totalCost, nodeIds, costs, path\n"
               + "RETURN\n"
               + "    index,\n"
               + "    nodes(path) as path\n"
               + "ORDER BY index\n";
    }

    @Override
    public Collection<GraphService.Waypoint> parseResult(final ResultSet resultSet) {
        try {
            while (resultSet.next()) {
                var a = (ArrayList<LinkedHashMap<Long, Object>>) resultSet.getObject("path");
                return a.stream()
                        .map(el -> {
                            return GraphService.Waypoint.builder()
                                    .lat((Double)el.get("lat"))
                                    .lng((Double)el.get("lng"))
                                    .build();
                        })
                        .toList();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Collections.emptyList();
    }
}
