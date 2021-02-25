package com.test.nals.repository;

import org.springframework.stereotype.Component;

import java.util.ResourceBundle;

@Component
public class DaoUtils {

    private static final ResourceBundle SQL_RESOURCES = ResourceBundle
            .getBundle("sql_statement");

    public String getSQLStatement(String key) {
        String sql = null;

        if (SQL_RESOURCES != null) {
            sql = SQL_RESOURCES.getString(key);
        }

        return sql;
    }
}
