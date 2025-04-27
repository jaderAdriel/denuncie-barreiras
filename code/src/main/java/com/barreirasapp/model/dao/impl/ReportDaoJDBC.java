package com.barreirasapp.model.dao.impl;

import com.barreirasapp.infra.db.DatabaseConnection;
import com.barreirasapp.infra.exceptions.DatabaseException;
import com.barreirasapp.model.dao.ReportDao;
import com.barreirasapp.model.entities.Report;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReportDaoJDBC implements ReportDao {

    private final Connection conn;

    public ReportDaoJDBC(Connection conn) {
        this.conn = conn;
    }


    @Override
    public void insert(Report report) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(
                    """
                          INSERT INTO Report (type, ambient, severity, anonymous_report, event_detailing, reporter_fk)
                          VALUES (?, ?, ?, ?, ?, ?);
                      """, Statement.RETURN_GENERATED_KEYS
            );

            st.setString(1, report.getType());
            st.setString(2, report.getAmbient());
            st.setInt (3, report.getSeverity());
            st.setBoolean(4, report.getAnonymousReport());
            st.setString(5, report.getEventDetailing());
            st.setInt(6, report.getReporterId());

            int rowsAffected =  st.executeUpdate();

            if (rowsAffected > 0) {
                rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    report.setId(id);
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
    public void update(Report report) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement(
                    """
                          UPDATE Report
                          SET type = ?, ambient = ?, severity = ?, event_detailing = ?
                          WHERE id = ?;
                      """, Statement.RETURN_GENERATED_KEYS
            );

            st.setString(1, report.getType());
            st.setString(2, report.getAmbient());
            st.setInt(3, report.getSeverity());
            st.setString(4, report.getEventDetailing());
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
            st = conn.prepareStatement("DELETE FROM Report WHERE id = ?");
            st.setInt(1, id);

            st.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            DatabaseConnection.closeStatement(st);
        }
    }

    @Override
    public Report findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    """
                            SELECT id, type, ambient, severity, anonymous_report, event_detailing, reporter_fk
                            FROM Report
                            WHERE id = ?;
                      """
            );

            st.setInt(1, id);
            rs = st.executeQuery();

            if (rs.next()) {
                return instantiateReport(rs);
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
    public List<Report> findAll() {
        Statement st = null;
        ResultSet rs = null;
        List<Report> UserList = new ArrayList<>();
        try {
            st = conn.createStatement();
            rs = st.executeQuery(
                    """
                            SELECT id, reporter_fk, type, ambient, severity, anonymous_report, event_detailing, related_scenario_fk
                            FROM Report
                      """
            );

            while (rs.next()) {
                UserList.add(instantiateReport(rs));
            }
            return UserList;
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            DatabaseConnection.closeResultSet(rs);
            DatabaseConnection.closeStatement(st);
        }
    }

    public List<Report> findAll() {
        Statement st = null;
        ResultSet rs = null;
        List<Report> UserList = new ArrayList<>();
        try {
            st = conn.createStatement();
            rs = st.executeQuery(
                    """
                            SELECT id, reporter_fk, type, ambient, severity, anonymous_report, event_detailing, related_scenario_fk
                            FROM Report
                      """
            );

            while (rs.next()) {
                UserList.add(instantiateReport(rs));
            }
            return UserList;
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            DatabaseConnection.closeResultSet(rs);
            DatabaseConnection.closeStatement(st);
        }
    }

    private Report instantiateReport(ResultSet rs) throws SQLException {
        Report report = new Report();
        report.setId(rs.getInt("id"));
        report.setType(rs.getString("type"));
        report.setAmbient(rs.getString("ambient"));
        report.setSeverity(rs.getInt("severity"));
        report.setAnonymousReport(rs.getBoolean("anonymous_report"));
        report.setEventDetailing(rs.getString("event_detailing"));

        return report;
    }
}
