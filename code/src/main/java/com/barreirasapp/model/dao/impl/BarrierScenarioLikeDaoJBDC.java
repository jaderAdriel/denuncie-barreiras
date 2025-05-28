package com.barreirasapp.model.dao.impl;

import com.barreirasapp.infra.db.DatabaseConnection;
import com.barreirasapp.infra.exceptions.DatabaseException;
import com.barreirasapp.model.dao.BarrierScenarioLikeDao;
import com.barreirasapp.model.dao.DaoFactory;
import com.barreirasapp.model.dao.UserDao;
import com.barreirasapp.model.entities.BarrierScenario;
import com.barreirasapp.model.entities.Comment;
import com.barreirasapp.model.entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BarrierScenarioLikeDaoJBDC implements BarrierScenarioLikeDao {
    private final Connection conn;
    private final UserDao userDao;

    public BarrierScenarioLikeDaoJBDC(Connection conn, UserDao userDao) {
        this.conn = conn;
        this.userDao = userDao;
    }

    @Override
    public void insert(User user, BarrierScenario barrierScenario)  {
        PreparedStatement st = null;
        ResultSet rs;

        try {
            conn.setAutoCommit(false);

            st = conn.prepareStatement(
                    """
                          INSERT INTO BarrierScenario_Like (user_fk, scenario_fk)
                          VALUES (?, ?)
                      """, Statement.RETURN_GENERATED_KEYS
            );

            st.setInt(1, user.getId());
            st.setInt(2, barrierScenario.getId());

            st.executeUpdate();

            conn.commit();

        } catch (SQLException e) {

            throw new DatabaseException(e.getMessage());
        } finally {
            DatabaseConnection.closeStatement(st);
        }
    }


    @Override
    public void deleteById(User user, BarrierScenario barrierScenario) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    """
                              DELETE FROM BarrierScenario_Like
                              WHERE user_fk = ? AND scenario_fk = ?
                      """, Statement.RETURN_GENERATED_KEYS
            );
            st.setInt( 1 , user.getId());
            st.setInt( 2 , barrierScenario.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            DatabaseConnection.closeStatement(st);
        }
    }

    @Override
    public Set<User> findByBarrierScenarioId(Integer barrierScenarioId) {
        PreparedStatement st = null;
        ResultSet rs = null;
        Set<User> userList = new HashSet<>();
        try {
            st = conn.prepareStatement(
                    """
                            SELECT user_fk, scenario_fk
                            FROM BarrierScenario_Like
                            WHERE scenario_fk = ?
                      """
            );

            st.setInt(1, barrierScenarioId);
            rs = st.executeQuery();

            while (rs.next()) {
                User user = userDao.findById(rs.getInt("user_fk"));
                userList.add(user);
            }

            return userList;

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            DatabaseConnection.closeResultSet(rs);
            DatabaseConnection.closeStatement(st);
        }
    }
}
