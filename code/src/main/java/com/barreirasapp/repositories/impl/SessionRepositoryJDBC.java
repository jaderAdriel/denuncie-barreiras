package com.barreirasapp.repositories.impl;

import com.barreirasapp.infra.db.QueryExecutor;
import com.barreirasapp.repositories.UserRepository;
import com.barreirasapp.infra.db.DataSource;
import com.barreirasapp.infra.exceptions.DatabaseException;
import com.barreirasapp.entities.Session;
import com.barreirasapp.repositories.SessionRepository;
import com.barreirasapp.entities.User;

import java.sql.*;
import java.util.*;

public class SessionRepositoryJDBC implements SessionRepository {

    private final UserRepository userRepository;

    public SessionRepositoryJDBC(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<Session> findById(String sessionId) {
        String sql = """
                            SELECT Session.id AS session_id, user_id, U.birth_date AS user_birth_date, U.email AS user_email,
                                   U.gender AS user_gender, U.name AS user_name, U.password as user_password, U.role AS user_role
                            FROM Session
                            JOIN User U on U.id = Session.user_id
                            WHERE Session.id = ?;
                  """;

        try (Connection conn = DataSource.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)
        ) {
            st.setString(1, sessionId);

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(instantiateSession(rs));
                }
            }

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }

        return Optional.empty();
    }

    @Override
    public void insert(Session session) {
        String sql = """
                          INSERT INTO Session (id, user_id) VALUES (?, ?);
                      """;

        try (Connection conn = DataSource.getConnection();
             PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            st.setString(1, session.getSessionId());
            st.setInt(2, session.getUserId());
            QueryExecutor.executeUpdate(st, conn);
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public void deleteById(String sessionId) {
        String sql = """
                          DELETE FROM Session WHERE id = ?
                      """;

        try (Connection conn = DataSource.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)
        ) {
            st.setString(1, sessionId);

            QueryExecutor.executeUpdate(st, conn);
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public void deleteAllByUser(User user) {
        String sql = """
                          DELETE FROM Session WHERE user_id = ?
                      """;

        try (Connection conn = DataSource.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)
        ) {
            st.setInt(1, user.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    private Session instantiateSession(ResultSet rs) throws SQLException {
        String sessionId = rs.getString("session_id");

        User user = userRepository.findById(rs.getInt("user_id")).orElseThrow();

        return new Session(sessionId, user);
    }
}
