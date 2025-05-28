package com.barreirasapp.infra.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QueryExecutor {
    public static int executeUpdateWithGeneratedKey(PreparedStatement stmt, Connection conn) throws SQLException {

        conn.setAutoCommit(false);

        int affectedRows = stmt.executeUpdate();

        if (affectedRows == 0) {
            throw new SQLException("A operação não afetou nenhuma linha.");
        }

        try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                conn.rollback();
                throw new SQLException("Nenhum ID foi retornado.");
            }
        }
    }


}
