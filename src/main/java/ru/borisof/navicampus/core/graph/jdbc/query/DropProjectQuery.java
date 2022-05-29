package ru.borisof.navicampus.core.graph.jdbc.query;

import lombok.AllArgsConstructor;
import ru.borisof.navicampus.core.graph.jdbc.Neo4jNativeQuery;

import java.sql.ResultSet;

public class DropProjectQuery extends Neo4jNativeQuery<Void> {

    @Override
    protected String createStatement() {
        return "CALL gds.graph.drop('routeGraph') YIELD graphName;";
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
