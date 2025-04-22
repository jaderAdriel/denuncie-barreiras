package com.barreirasapp.infra.db;

import com.barreirasapp.infra.exceptions.DatabaseException;

import java.sql.*;

public class DatabaseConnection {

    private static Connection conn = null;

    public static Connection getConnection() {
        if (conn != null) return conn;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            conn = DriverManager.getConnection(
                    System.getenv("DB_URL"),
                    System.getenv("DB_USER"),
                    System.getenv("DB_PASSWORD"));

        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao sgbd");
            throw new DatabaseException(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Erro ao carregar driver");
        }

        return  conn;
    }

    public static void closeConnection() {
        if (conn == null) return;

        try {
            conn.close();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public static void closeStatement(Statement st) {
        if (st == null) return;

        try {
            st.close();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public static void closeResultSet(ResultSet rs) {
        if (rs == null) return;

        try {
            rs.close();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }
}