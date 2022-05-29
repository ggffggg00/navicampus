package ru.borisof.navicampus.core.graph.jdbc;

import lombok.Data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public abstract class Neo4jNativeQuery<T> {

    protected String getStatement(){
        return createStatement();
    }

    protected abstract String createStatement();

    protected boolean ignoreExceptions() {
        return false;
    }

    public abstract T parseResult(ResultSet resultSet);


}
