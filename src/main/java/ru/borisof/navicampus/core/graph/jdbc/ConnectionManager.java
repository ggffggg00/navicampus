package ru.borisof.navicampus.core.graph.jdbc;

import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Service
public class ConnectionManager {

    private Connection connection;

    public Connection getNeo4jConnection(){
        try {
            if (connection == null || connection.isClosed()){
                connection = createConnection();
            }
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Connection createConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:neo4j:bolt://localhost", "neo4j", "secret");
    }


}
