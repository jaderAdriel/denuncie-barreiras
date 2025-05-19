package com.barreirasapp.model.dao.impl;

import com.barreirasapp.infra.db.DatabaseConnection;
import com.barreirasapp.infra.exceptions.DatabaseException;
import com.barreirasapp.model.dao.BarrierScenarioDao;
import com.barreirasapp.model.dao.DaoFactory;
import com.barreirasapp.model.dao.UserDao;
import com.barreirasapp.model.entities.BarrierScenario;
import com.barreirasapp.model.entities.Law;
import com.barreirasapp.model.entities.User;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BarrierScenarioDaoJDBC implements BarrierScenarioDao {

    private final Connection conn;

    public BarrierScenarioDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(BarrierScenario scenario) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            conn.setAutoCommit(false);
            st = conn.prepareStatement(
                    """
                              INSERT INTO BarrierScenario (type, author_fk, content, title)
                              VALUES (?, ?, ?, ?);
                      """, Statement.RETURN_GENERATED_KEYS
            );


            st.setString(1, scenario.getType());
            st.setInt(2, scenario.getAuthor().getId());
            st.setString (3, scenario.getContent());
            st.setString(4, scenario.getTitle());

            for (Law law : scenario.getAssociatedLaws()) {
                associateToLaw(scenario, law);
            }

            int rowsAffected =  st.executeUpdate();

            if (rowsAffected > 0) {
                rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    scenario.setId(id);
                }
                conn.commit();
            } else {
                conn.rollback();
                throw new DatabaseException("Unexpect error: No rows affected");
            }


        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new DatabaseException(e.getMessage());
        } finally {
            DatabaseConnection.closeResultSet(rs);
            DatabaseConnection.closeStatement(st);
        }
    }

    public void associateToLaw(BarrierScenario scenario, Law law) throws SQLException {
        PreparedStatement st = conn.prepareStatement(
                """
                          INSERT INTO BarrierScenario_Law (barrierScenario_fk, law_fk)
                          VALUES (?, ?);
                  """, Statement.RETURN_GENERATED_KEYS
        );

        st.setInt( 1 , scenario.getId());
        st.setString( 2 , law.getCode());

        st.execute();
    }

    @Override
    public void update(BarrierScenario scenario) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement(
                    """
                          UPDATE BarrierScenario
                          SET type = ?, content = ?, title = ?
                          WHERE id = ?;
                      """, Statement.RETURN_GENERATED_KEYS
            );

            st.setString(1, scenario.getType());
            st.setString(2, scenario.getContent());
            st.setString(3, scenario.getTitle());
            st.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            DatabaseConnection.closeStatement(st);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("DELETE FROM BarrierScenario WHERE id = ?");
            st.setInt(1, id);

            st.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            DatabaseConnection.closeStatement(st);
        }
    }

    @Override
    public BarrierScenario findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    """
                            SELECT id, author_fk, type, content, title, creation_date, likes
                            FROM BarrierScenario
                            WHERE id = ?;
                      """
            );

            st.setInt(1, id);
            rs = st.executeQuery();

            if (rs.next()) {
                return instantiateScenario(rs);
            }

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            DatabaseConnection.closeResultSet(rs);
            DatabaseConnection.closeStatement(st);
        }

        return null;
    }

    @Override
    public List<BarrierScenario> findAllByAuthorId(Integer id) {
        Statement st = null;
        ResultSet rs = null;
        List<BarrierScenario> scenarioList = new ArrayList<>();

        try {
            st = conn.createStatement();
            rs = st.executeQuery(
                    """
                            SELECT id, author_fk, type, content, title, creation_date, likes
                            FROM BarrierScenario
                            WHERE author_fk = ?;
                      """
            );

            while (rs.next()) {
                scenarioList.add(instantiateScenario(rs));
            }
            return scenarioList;
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            DatabaseConnection.closeResultSet(rs);
            DatabaseConnection.closeStatement(st);
        }
    }

    public List<BarrierScenario> findAll() {
        Statement st = null;
        ResultSet rs = null;
        List<BarrierScenario> moderatorList = new ArrayList<>();
        try {
            st = conn.createStatement();
            rs = st.executeQuery(
                    """
                            SELECT id, author_fk, type, content, title, creation_date, likes
                            FROM BarrierScenario;
                      """
            );

            while (rs.next()) {
                moderatorList.add(instantiateScenario(rs));
            }
            return moderatorList;
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            DatabaseConnection.closeResultSet(rs);
            DatabaseConnection.closeStatement(st);
        }
    }

    private BarrierScenario instantiateScenario(ResultSet rs) throws SQLException {
        UserDao userDao = DaoFactory.createUserDao();

        int authorId = rs.getInt("author_fk");
        User author = userDao.findById(authorId);

        LocalDateTime creationDate = rs.getTimestamp("creation_date").toLocalDateTime();

        int scenarioId = rs.getInt("id");
        String scenarioType = rs.getString("type");
        String scenarioContent = rs.getString("content");
        String scenarioTitle = rs.getString("title");

        List<Law> associatedLaws = DaoFactory.createLawDao().findByBarrierScenario(scenarioId);

        return new BarrierScenario(scenarioId, scenarioType, author, scenarioContent, scenarioTitle, creationDate, associatedLaws );
    }

}
