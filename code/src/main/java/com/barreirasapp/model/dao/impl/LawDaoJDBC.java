package com.barreirasapp.model.dao.impl;

import com.barreirasapp.infra.db.DatabaseConnection;
import com.barreirasapp.infra.exceptions.DatabaseException;
import com.barreirasapp.model.dao.LawDao;
import com.barreirasapp.model.dao.UserDao;
import com.barreirasapp.model.entities.Law;
import com.barreirasapp.model.entities.User;
import com.barreirasapp.model.entities.valueobjects.Email;
import com.barreirasapp.model.enums.Gender;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LawDaoJDBC implements LawDao{

    private final Connection conn;

    public LawDaoJDBC(Connection conn) {
        this.conn = conn;
    }


    @Override
    public void insert(Law law) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(
                    """
                          INSERT INTO Law (code, date, officialLink, title, description)
                          VALUES (?, ?, ?, ?, ?);
                      """, Statement.RETURN_GENERATED_KEYS
            );

            LocalDate lawDate = law.getDate();
            Date sqlDate = Date.valueOf(lawDate);

            st.setString(1, law.getCode());
            st.setDate(2, sqlDate);
            st.setString(3, law.getOfficialLink());
            st.setString(4, law.getTitle());
            st.setString(5, law.getDescription());

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
    public void update(Law law) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement(
                    """
                          UPDATE Law
                          SET description = ?, officialLink = ?
                          WHERE code = ?;
                      """, Statement.RETURN_GENERATED_KEYS
            );

            st.setString(1, law.getDescription());
            st.setString(2, law.getOfficialLink());
            st.setString(3, law.getCode());

            int rowsAffected =  st.executeUpdate();

            if (rowsAffected == 0) {
                throw new DatabaseException("Unexpect error: No rows affected");
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            DatabaseConnection.closeStatement(st);
        }
    }

    @Override
    public void deleteById(String id) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("DELETE FROM Law WHERE code = ?");
            st.setString(1, id);

            st.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            DatabaseConnection.closeStatement(st);
        }
    }

    @Override
    public Law findById(String id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    """
                            SELECT code, date, officialLink, title, description
                            FROM Law
                            WHERE code = ?;
                      """
            );

            st.setString(1, id);
            rs = st.executeQuery();

            if (rs.next()) {
                return instantiateLaw(rs);
            }

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            DatabaseConnection.closeResultSet(rs);
            DatabaseConnection.closeStatement(st);
        }

        return null;
    }

    public List<Law> findByBarrierScenario(int barrierScenarioID) {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Law> lawList = new ArrayList<>();
        try {
            st = conn.prepareStatement(
                    """
                            SELECT BL.law_fk, code, date, officialLink, title, description
                            FROM BarrierScenario_Law as BL
                            JOIN Law on BL.law_fk = Law.code
                            WHERE BL.barrierScenario_fk = ?;
                      """
            );

            st.setInt(1, barrierScenarioID);
            rs = st.executeQuery();

            while (rs.next()) {
                lawList.add(instantiateLaw(rs));
            }

            return lawList;

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            DatabaseConnection.closeResultSet(rs);
            DatabaseConnection.closeStatement(st);
        }
    }

    @Override
    public List<Law> findAll() {
        Statement st = null;
        ResultSet rs = null;
        List<Law> lawList = new ArrayList<>();
        try {
            st = conn.createStatement();
            rs = st.executeQuery(
                    """
                            SELECT code, date, officialLink, title, description
                            FROM Law
                      """
            );

            while (rs.next()) {
                lawList.add(instantiateLaw(rs));
            }
            return lawList;
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            DatabaseConnection.closeResultSet(rs);
            DatabaseConnection.closeStatement(st);
        }
    }

    private Law instantiateLaw(ResultSet rs) throws SQLException {

        String lawCode = rs.getString("code");
        String lawTitle = rs.getString("title");
        String lawDescription = rs.getString("description");
        String lawOfficialLink = rs.getString("officialLink");
        LocalDate lawDate = LocalDate.parse(rs.getString("date"));

        return new Law(lawCode, lawDate, lawOfficialLink, lawTitle, lawDescription);
    }
}
