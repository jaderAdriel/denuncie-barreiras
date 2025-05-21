package com.barreirasapp.model.dao.impl;

import com.barreirasapp.infra.db.DatabaseConnection;
import com.barreirasapp.infra.exceptions.DatabaseException;
import com.barreirasapp.model.dao.ReportDao;
import com.barreirasapp.model.entities.Report;
import com.barreirasapp.model.enums.BarrierType;
import com.barreirasapp.model.enums.EnvironmentType;

import java.sql.*;
import java.time.LocalDateTime;
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
            System.out.println(report.getAnonymousReport());
            if(!report.getAnonymousReport()){
                st = conn.prepareStatement(
                        """
                              INSERT INTO Report (type, ambient, anonymous_report, event_detailing, reporter_fk)
                              VALUES (?, ?, ?, ?, ?);
                          """, Statement.RETURN_GENERATED_KEYS
                );

                st.setInt(5, report.getReporterId());
            }else {
                st = conn.prepareStatement(
                        """
                              INSERT INTO Report (type, ambient, anonymous_report, event_detailing)
                              VALUES (?, ?, ?, ?);
                          """, Statement.RETURN_GENERATED_KEYS
                );
            }

            String barrierType = report.getType() == null ? null : report.getType().toString();
            String ambient = report.getAmbient() == null ? null : report.getAmbient().toString();

            st.setString(1, barrierType);
            st.setString(2,  ambient);
            st.setBoolean(3, report.getAnonymousReport());
            st.setString(4, report.getEventDetailing());

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
                          SET type = ?, ambient = ?, event_detailing = ?
                          WHERE id = ?;
                      """, Statement.RETURN_GENERATED_KEYS
            );

            st.setString(1, String.valueOf(report.getType()));
            st.setString(2, String.valueOf(report.getAmbient()));
            st.setString(3, report.getEventDetailing());
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
                            SELECT id, type, ambient, anonymous_report, event_detailing, reporter_fk, creation_date
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
    public List<Report> findAllByReporterId(Integer id) {
        Statement st = null;
        ResultSet rs = null;
        List<Report> reportList = new ArrayList<>();

        try {
            st = conn.createStatement();
            rs = st.executeQuery(
                    """
                            SELECT id, reporter_fk, type, ambient, anonymous_report, event_detailing, related_scenario_fk, creation_date
                            FROM Report
                            WHERE reporter_fk = ?;
                      """
            );

            while (rs.next()) {
                reportList.add(instantiateReport(rs));
            }
            return reportList;
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
                            SELECT id, reporter_fk, type, ambient, anonymous_report, event_detailing, related_scenario_fk, creation_date
                            FROM Report;
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

        String typeValue = rs.getString("type");
        if(!(typeValue.isEmpty() || typeValue.equals("null"))) {
            report.setType(BarrierType.valueOf(typeValue));
        }

        report.setAmbient(EnvironmentType.valueOf(rs.getString("ambient")));
        report.setAnonymousReport(rs.getBoolean("anonymous_report"));
        report.setEventDetailing(rs.getString("event_detailing"));

        LocalDateTime creationDate = rs.getTimestamp("creation_date").toLocalDateTime();

        return report;
    }
}
