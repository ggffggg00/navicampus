package ru.borisof.navicampus.core.graph.jdbc.query;

import lombok.Builder;
import ru.borisof.navicampus.core.graph.jdbc.Neo4jNativeQuery;
import ru.borisof.navicampus.core.graph.service.GraphService;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

@Builder
public class GetAllRoutesAtFloorQuery extends Neo4jNativeQuery<Collection<GraphService.Route>> {

    private int buildingId;
    private int floor;

    @Override
    protected String createStatement() {
        return "MATCH (:Waypoint {floor: " + floor + ", buildingId: " + buildingId + "})-[r]-()\n"
               + "RETURN ID(startNode(r)) as start, ID(endNode(r)) as end, r.cost as cost";
    }

    @Override
    public Collection<GraphService.Route> parseResult(final ResultSet rs) {
        var res = new ArrayList<GraphService.Route>();
        try {
            while (rs.next()) {
                res.add(
                        GraphService.Route.builder()
                                .nodeIdStart(rs.getInt("start"))
                                .nodeIdEnd(rs.getInt("end"))
                                .cost(rs.getDouble("cost"))
                                .build());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }
}
