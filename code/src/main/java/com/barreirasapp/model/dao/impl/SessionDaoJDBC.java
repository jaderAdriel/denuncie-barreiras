package com.barreirasapp.model.dao.impl;

import com.barreirasapp.infra.db.DatabaseConnection;
import com.barreirasapp.infra.exceptions.DatabaseException;
import com.barreirasapp.model.Session;
import com.barreirasapp.model.dao.DaoFactory;
import com.barreirasapp.model.dao.SessionDao;
import com.barreirasapp.model.entities.User;

import java.sql.*;
import java.util.*;

public class SessionDaoJDBC implements SessionDao {

    private final Connection conn;

    public SessionDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Optional<Session> findByid(String sessionId) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    """
                            SELECT id, user_id
                            FROM Session
                            WHERE id = ?;
                      """
            );

            st.setString(1, sessionId);
            rs = st.executeQuery();

            if (rs.next()) {
                return Optional.of(instantiateSession(rs));
            }

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            DatabaseConnection.closeResultSet(rs);
            DatabaseConnection.closeStatement(st);
        }

        return Optional.empty();
    }

    @Override
    public void insert(Session session) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    """
                          INSERT INTO Session (id, user_id)
                          VALUES (?, ?);
                      """
            );

            st.setString(1, session.getSessionId());
            st.setInt(2, session.getUserId());

            int rowsAffected =  st.executeUpdate();

            if (rowsAffected == 0) {
                throw new DatabaseException("Unexpect error: No rows affected");
            }

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            DatabaseConnection.closeResultSet(rs);
            DatabaseConnection.closeStatement(st);
        }
    }

    @Override
    public void deleteById(String sessionId) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("DELETE FROM Session WHERE id = ?");
            st.setString(1, sessionId);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            DatabaseConnection.closeStatement(st);
        }
    }

    @Override
    public void deleteAllByUser(User user) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("DELETE FROM Session WHERE user_id = ?");
            st.setInt(1, user.getId());
            st.executeUpdate();
            System.out.println("Sessões removidas");
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            DatabaseConnection.closeStatement(st);
        }
    }

    private Session instantiateSession(ResultSet rs) throws SQLException {
        String sessionId = rs.getString("id");
        int userId = rs.getInt("user_id");
        User user = DaoFactory.createUserDao().findById(userId);
        return new Session(sessionId, user);
    }
}
