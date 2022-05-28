package ru.borisof.navicampus.core.graph.jdbc.query;

import lombok.Builder;
import lombok.Data;
import ru.borisof.navicampus.core.graph.jdbc.Neo4jNativeQuery;

import java.sql.ResultSet;

@Builder
public class CreateEdgeQuery extends Neo4jNativeQuery<Boolean> {

    private long startId;
    private long endId;
    private double cost;

    @Override
    protected String createStatement() {
        return "MATCH"
               + " (a:Waypoint), (b:Waypoint)"
               + " WHERE ID(a) = "+startId+" AND ID(b) = "+endId+""
               + " CREATE (a)-[r:NAVIGATES_TO {cost: "+cost+"}]->(b)"
               + " RETURN type(r), r.name";
    }

    @Override
    public Boolean parseResult(final ResultSet resultSet) {
        return true;
    }
}
