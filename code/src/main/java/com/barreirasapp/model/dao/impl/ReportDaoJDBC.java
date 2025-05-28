package com.barreirasapp.model.dao.impl;

import com.barreirasapp.infra.db.DatabaseConnection;
import com.barreirasapp.infra.exceptions.DatabaseException;
import com.barreirasapp.infra.security.UserContext;
import com.barreirasapp.model.dao.DaoFactory;
import com.barreirasapp.model.dao.ReportDao;
import com.barreirasapp.model.dao.UserDao;
import com.barreirasapp.model.entities.Moderator;
import com.barreirasapp.model.entities.Report;
import com.barreirasapp.model.entities.ReportReview;
import com.barreirasapp.model.entities.User;
import com.barreirasapp.model.enums.BarrierType;
import com.barreirasapp.model.enums.EnvironmentType;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReportDaoJDBC implements ReportDao {
    private UserDao userDao;
    private final Connection conn;

    public ReportDaoJDBC(Connection conn) {
        this.conn = conn;
        this.userDao = DaoFactory.createUserDao();
    }


    @Override
    public void insert(Report report) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            System.out.println(report.getAnonymousReport());
            st = conn.prepareStatement(
                    """
                          INSERT INTO Report (type, ambient, anonymous_report, event_detailing, related_scenario_fk, reporter_fk)
                          VALUES (?, ?, ?, ?, ?, ?);
                      """, Statement.RETURN_GENERATED_KEYS
            );

            String barrierType = report.getType() == null ? null : report.getType().toString();
            String ambient = report.getAmbient() == null ? null : report.getAmbient().toString();

            st.setString(1, barrierType);
            st.setString(2,  ambient);
            st.setBoolean(3, report.getAnonymousReport());
            st.setString(4, report.getEventDetailing());
            if(report.getBarrierScenarioId() == null){
                st.setNull(5, Types.INTEGER);
            }else{
                st.setInt(5, report.getBarrierScenarioId());
            }
            if(report.getAnonymousReport()){
                st.setNull(6, Types.BOOLEAN);
            }else {
                st.setInt(6, report.getReporterId());
            }

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
                          SET review_author_fk = ?, review_comment = ?, review_is_valid = ?, review_create_at = ?
                          WHERE id = ?;
                      """, Statement.RETURN_GENERATED_KEYS
            );

            st.setInt(1, report.getReviewAuthorId());
            st.setString(2, report.getReviewComment());
            st.setBoolean(3, report.getReviewIsValid());
            st.setTimestamp(4, Timestamp.valueOf(report.getReviewCreationDate()));
            st.setInt(5, report.getId());
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
                            SELECT *
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
                            SELECT *
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
                            SELECT *
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

    public ReportReview findReviewByReportId(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        ReportReview review = new ReportReview();

        try {
            st = conn.prepareStatement(
                    """
                            SELECT review_comment, review_author_fk, review_createAt, review_published, review_is_valid
                            FROM Report
                            WHERE id = ?;
                      """
            );

            st.setInt(1, id);

            rs = st.executeQuery();

            if (rs.next()) {
                return instantiateReport(rs).getReview();
            }

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            DatabaseConnection.closeResultSet(rs);
            DatabaseConnection.closeStatement(st);
        }

        return null;
    }

    public List<Report> findAllValid() {
        List<Report> list = new ArrayList<>();
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement("SELECT * FROM Report WHERE review_is_valid = TRUE");

            rs = st.executeQuery();
            while (rs.next()) {
                Report report = instantiateReport(rs);
                list.add(report);
            }

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            DatabaseConnection.closeResultSet(rs);
            DatabaseConnection.closeStatement(st);
        }

        return list;
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

        if (!report.getAnonymousReport()) {
            User reporter = userDao.findById(rs.getInt("reporter_fk"));
            report.setReporter(reporter);
        }

        LocalDateTime creationDate = rs.getTimestamp("creation_date").toLocalDateTime();
        report.setCreationDate(creationDate);

        Integer reviewAuthorFk = rs.getInt("review_author_fk");
        System.out.println(reviewAuthorFk);
        if (reviewAuthorFk != 0) {
            User user = userDao.findById(reviewAuthorFk);
            Moderator reviewer = new Moderator(
                    user.getPassword(),
                    user.getGender(),
                    user.getBirthDate(),
                    user.getEmail(),
                    user.getName(),
                    "77777");

            try {
                reviewer.setId(reviewAuthorFk);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }

            Timestamp createAtTimeStamp = rs.getTimestamp("review_create_at");
            LocalDateTime createAt = null;
            if (createAtTimeStamp != null) {
                createAt = createAtTimeStamp.toLocalDateTime();
            }

            Boolean isValid = rs.getBoolean("review_is_valid");
            String comment = rs.getString("review_comment");
            report.setReportReview(reviewer, createAt, isValid, comment);
        }

        return report;
    }
}
