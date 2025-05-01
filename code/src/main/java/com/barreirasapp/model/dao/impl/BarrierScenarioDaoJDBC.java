package com.barreirasapp.model.dao.impl;

import com.barreirasapp.infra.db.DatabaseConnection;
import com.barreirasapp.infra.exceptions.DatabaseException;
import com.barreirasapp.model.dao.BarrierScenarioDao;
import com.barreirasapp.model.dao.DaoFactory;
import com.barreirasapp.model.dao.UserDao;
import com.barreirasapp.model.entities.BarrierScenario;
import com.barreirasapp.model.entities.User;

import java.sql.*;
import java.time.LocalDate;
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
            st = conn.prepareStatement(
                    """
                          INSERT INTO BarrierScenario (type, author, content, title, creation_date)
                          VALUES (?, ?, ?, ?, ?);
                      """, Statement.RETURN_GENERATED_KEYS
            );

            LocalDate birthDate = scenario.getCreationDate(); // Seu LocalDate
            java.sql.Date sqlDate = java.sql.Date.valueOf(birthDate);

            st.setString(1, scenario.getType());
            st.setString(2, scenario.getAuthor().toString());
            st.setString (3, scenario.getContent());
            st.setString(4, scenario.getTitle());
            st.setDate(5, sqlDate);

            int rowsAffected =  st.executeUpdate();

            if (rowsAffected > 0) {
                rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    scenario.setId(id);
                }
            } else {
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
    public void update(BarrierScenario scenario) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement(
                    """
                          UPDATE Report
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
                            SELECT id, type, author_fk, content, title, creation_date, likes                            FROM BarrierScenario
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

        LocalDate creationDate = LocalDate.parse(rs.getString("creation_date"));

        BarrierScenario scenario = new BarrierScenario();
        scenario.setId(rs.getInt("id"));
        scenario.setType(rs.getString("type"));
        scenario.setContent(rs.getString("content"));
        scenario.setAuthor(author);
        scenario.setTitle(rs.getString("title"));

        return scenario;
    }

}
