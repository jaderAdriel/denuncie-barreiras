package com.barreirasapp.repositories.impl;

import com.barreirasapp.infra.db.DataSource;
import com.barreirasapp.infra.db.QueryExecutor;
import com.barreirasapp.infra.exceptions.DatabaseException;
import com.barreirasapp.repositories.BarrierScenarioLikeRepository;
import com.barreirasapp.repositories.UserRepository;
import com.barreirasapp.entities.BarrierScenario;
import com.barreirasapp.entities.User;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class BarrierScenarioLikeRepositoryJBDC implements BarrierScenarioLikeRepository {
    private final UserRepository userRepository;

    public BarrierScenarioLikeRepositoryJBDC(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void insert(User user, BarrierScenario barrierScenario)  {
        String sql = """
                          INSERT INTO BarrierScenario_Like (user_fk, scenario_fk)
                          VALUES (?, ?)
                      """;

        try (Connection conn = DataSource.getConnection();
             PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ){

            conn.setAutoCommit(false);

            st.setInt(1, user.getId());
            st.setInt(2, barrierScenario.getId());

            QueryExecutor.executeUpdate(st, conn);

            conn.commit();

        } catch (SQLException e) {

            throw new DatabaseException(e.getMessage());
        }
    }


    @Override
    public void deleteById(User user, BarrierScenario barrierScenario) {
        String sql = """
                              DELETE FROM BarrierScenario_Like
                              WHERE user_fk = ? AND scenario_fk = ?
                      """;

        try (Connection conn = DataSource.getConnection();
            PreparedStatement st = conn.prepareStatement(sql)
        ) {
            st.setInt( 1 , user.getId());
            st.setInt( 2 , barrierScenario.getId());
            QueryExecutor.executeUpdate(st, conn);
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public Set<User> findByBarrierScenarioId(Integer barrierScenarioId) {
        String sql = """
                            SELECT user_fk, scenario_fk
                            FROM BarrierScenario_Like
                            WHERE scenario_fk = ?
                      """;
        Set<User> userList = new HashSet<>();
        try (Connection conn = DataSource.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)
        ) {
            st.setInt(1, barrierScenarioId);

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    User user = userRepository.findById(rs.getInt("user_fk")).orElseThrow();
                    userList.add(user);
                }
            }

            return userList;

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }
}
