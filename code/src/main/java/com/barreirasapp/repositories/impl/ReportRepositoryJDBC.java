package com.barreirasapp.repositories.impl;

import com.barreirasapp.infra.db.DataSource;
import com.barreirasapp.infra.db.QueryExecutor;
import com.barreirasapp.infra.exceptions.DatabaseException;
import com.barreirasapp.repositories.ReportRepository;
import com.barreirasapp.repositories.UserRepository;
import com.barreirasapp.entities.Moderator;
import com.barreirasapp.entities.Report;
import com.barreirasapp.entities.User;
import com.barreirasapp.entities.enums.BarrierType;
import com.barreirasapp.entities.enums.EnvironmentType;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReportRepositoryJDBC implements ReportRepository {
    private final UserRepository userRepository;

    public ReportRepositoryJDBC(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public Integer insert(Report report) {
        String sql = """
                          INSERT INTO Report (type, ambient, anonymous_report, event_detailing, related_scenario_fk, reporter_fk)
                          VALUES (?, ?, ?, ?, ?, ?);
                      """;
        try (Connection conn = DataSource.getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
        ) {
            String barrierType = report.getType() == null ? null : report.getType().toString();
            String ambient = report.getAmbient() == null ? null : report.getAmbient().toString();

            st.setString(1, barrierType);
            st.setString(2,  ambient);
            st.setBoolean(3, report.getAnonymousReport());
            st.setString(4, report.getEventDetailing());

            QueryExecutor.setIntOrNull(st, 5, report.getBarrierScenarioId());
            QueryExecutor.setIntOrNull(st, 6, report.getReporterId());

            return QueryExecutor.executeUpdateWithGeneratedKey(st, conn);
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public void update(Report report) {
        String sql = """
                          UPDATE Report
                          SET review_author_fk = ?, review_comment = ?, review_is_valid = ?, review_create_at = ?
                          WHERE id = ?;
                     """;

        try (Connection conn = DataSource.getConnection();
            PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {

            st.setInt(1, report.getReviewAuthorId());
            st.setString(2, report.getReviewComment());
            st.setBoolean(3, report.getReviewIsValid());
            st.setTimestamp(4, Timestamp.valueOf(report.getReviewCreationDate()));
            st.setInt(5, report.getId());
            st.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public void deleteById(Integer id) {
        String sql = """
                        DELETE FROM Report WHERE id = ?
                    """;

        try (Connection conn = DataSource.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)
        ){
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public Optional<Report> findById(Integer id) {
        String sql = """
                        SELECT * FROM Report WHERE id = ?;
                     """;

        try (Connection conn = DataSource.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)
        ){
            st.setInt(1, id);

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(instantiateReport(rs));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }

        return Optional.empty();
    }

    @Override
    public List<Report> findAllByReporterId(Integer reporterFk) {
        String sql = """
                        SELECT * FROM Report WHERE reporter_fk = ?;
                     """;

        try (Connection conn = DataSource.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)
        ){
            st.setInt(1, reporterFk);
            try (ResultSet rs = st.executeQuery()) {
                List<Report> reportList = new ArrayList<>();
                while (rs.next()) {
                    reportList.add(instantiateReport(rs));
                }
                return reportList;
            }

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public List<Report> findAll() {
        return getReports("");
    }

    @Override
    public List<Report> findAllValid() {
        String condition = "WHERE review_is_valid = TRUE";
        return getReports(condition);
    }

    private List<Report> getReports(String condition) {
        String sql = "SELECT * FROM Report ";

        if (!condition.isEmpty())
            sql += "WHERE " + condition;

        try (Connection conn = DataSource.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)
        ){
            try (ResultSet rs = st.executeQuery()) {
                List<Report> reportList = new ArrayList<>();
                while (rs.next()) {
                    reportList.add(instantiateReport(rs));
                }
                return reportList;
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
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
        Optional<User> reporter = userRepository.findById(rs.getInt("reporter_fk"));

        if (!report.getAnonymousReport() && reporter.isPresent()) {
            report.setReporter(reporter.get());
        }

        LocalDateTime creationDate = rs.getTimestamp("creation_date").toLocalDateTime();
        report.setCreationDate(creationDate);

        Optional<Moderator> reviewer = userRepository.findModeratorById(rs.getInt("review_author_fk"));

        if (reviewer.isPresent()) {
            LocalDateTime createAt = rs.getTimestamp("review_create_at").toLocalDateTime();
            Boolean isValid = rs.getBoolean("review_is_valid");
            String comment = rs.getString("review_comment");
            report.setReportReview(reviewer.get(), createAt, isValid, comment);
        }

        return report;
    }
}
