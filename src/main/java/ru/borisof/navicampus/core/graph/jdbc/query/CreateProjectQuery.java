package ru.borisof.navicampus.core.graph.jdbc.query;

import lombok.AllArgsConstructor;
import ru.borisof.navicampus.core.graph.jdbc.Neo4jNativeQuery;

import java.sql.ResultSet;

public class CreateProjectQuery extends Neo4jNativeQuery<Void> {

    @Override
    protected String createStatement() {
        return "CALL gds.graph.project(\n"
               + "    'routeGraph',\n"
               + "    'Waypoint',\n"
               + "    { NAVIGATES_TO: {orientation: \"UNDIRECTED\"}},\n"
               + "    {\n"
               + "        relationshipProperties: 'cost'\n"
               + "    }\n"
               + ")";
    }

    @Override
    protected boolean ignoreExceptions() {
        return true;
    }

    @Override
    public Void parseResult(final ResultSet resultSet) {
        return null;
    }
}
