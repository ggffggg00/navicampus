package ru.borisof.navicampus.core.graph.jdbc.query;

import lombok.AllArgsConstructor;
import ru.borisof.navicampus.core.graph.jdbc.Neo4jNativeQuery;

import java.sql.ResultSet;

@AllArgsConstructor
public class DeleteEdgesByPlaceId extends Neo4jNativeQuery<Void> {
    private long placeId;

    @Override
    protected String createStatement() {
        return "match (a:Waypoint {navigationObjectId: " + placeId + "})-[r]->(b:Waypoint {navigationObjectId: "
               + "\"+placeId+\"}) delete r;";
    }

    @Override
    public Void parseResult(final ResultSet resultSet) {
        return null;
    }
}
