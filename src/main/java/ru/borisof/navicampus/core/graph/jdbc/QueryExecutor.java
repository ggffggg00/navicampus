package ru.borisof.navicampus.core.graph.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Service
@RequiredArgsConstructor
public class QueryExecutor {

    private final ConnectionManager connectionManager;

    public <T> T executeStm(Neo4jNativeQuery<T> query){
        Connection connection = connectionManager.getNeo4jConnection();
        var stmStr = query.getStatement();
        try (PreparedStatement stmt = connection.prepareStatement(stmStr)) {
            try (ResultSet rs = stmt.executeQuery()) {
               return query.parseResult(rs);
            }
        } catch (SQLException e) {
            if (!query.ignoreExceptions())
                throw new RuntimeException(e);
        }
        return null;
    }


}
