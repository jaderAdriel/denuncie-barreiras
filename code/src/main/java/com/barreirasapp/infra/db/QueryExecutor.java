package com.barreirasapp.infra.db;

import java.sql.*;
import java.util.Objects;

public class QueryExecutor {
    public static int executeUpdateWithGeneratedKey(PreparedStatement stmt, Connection conn) throws SQLException {
        conn.setAutoCommit(false);
        executeUpdate(stmt, conn);

        try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                conn.commit();
                return generatedKeys.getInt(1);
            } else {
                conn.rollback();
                throw new SQLException("Nenhum ID foi retornado.");
            }
        }
    }

    public static void executeUpdate(PreparedStatement stmt, Connection conn ) throws SQLException {
        int affectedRows = stmt.executeUpdate();

        if (affectedRows == 0) {
            throw new SQLException("A operação não afetou nenhuma linha.");
        }
    }

    public static void setStringOrNull(PreparedStatement st, int position, String value) throws SQLException {
        if (value == null) {
            st.setNull(position, Types.VARCHAR);
            return;
        }
        st.setString(position, value);
    }

    public static void setIntOrNull(PreparedStatement st, int position, Integer value) throws SQLException {
        if (value == null) {
            st.setNull(position, Types.INTEGER);
            return;
        }
        st.setInt(position, value);
    }
}
