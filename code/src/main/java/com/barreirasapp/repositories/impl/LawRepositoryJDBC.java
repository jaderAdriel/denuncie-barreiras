package com.barreirasapp.repositories.impl;

import com.barreirasapp.infra.db.DataSource;
import com.barreirasapp.infra.db.QueryExecutor;
import com.barreirasapp.infra.exceptions.DatabaseException;
import com.barreirasapp.repositories.LawRepository;
import com.barreirasapp.entities.Law;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LawRepositoryJDBC implements LawRepository {

    public LawRepositoryJDBC() {}

    @Override
    public String insert(Law law) {
        String sql = """
                          INSERT INTO Law (code, date, officialLink, title, description)
                          VALUES (?, ?, ?, ?, ?);
                      """;

        try (Connection conn = DataSource.getConnection();
            PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            LocalDate lawDate = law.getDate();
            Date sqlDate = Date.valueOf(lawDate);

            st.setString(1, law.getCode());
            st.setDate(2, sqlDate);
            st.setString(3, law.getOfficialLink());
            st.setString(4, law.getTitle());
            st.setString(5, law.getDescription());

            QueryExecutor.executeUpdate(st, conn);
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
        return null;
    }

    @Override
    public void update(Law law) {
        String sql = """
                          UPDATE Law SET description = ?, officialLink = ?
                          WHERE code = ?;
                      """;

        try (Connection conn = DataSource.getConnection();
             PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            st.setString(1, law.getDescription());
            st.setString(2, law.getOfficialLink());
            st.setString(3, law.getCode());
            QueryExecutor.executeUpdate(st, conn);
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public void deleteById(String id) {
        String sql = """
                        DELETE FROM Law WHERE code = ?
                     """;

        try (Connection conn = DataSource.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)
        ){
            st.setString(1, id);
            QueryExecutor.executeUpdate(st, conn);
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public Optional<Law> findById(String id) {
        String sql = """
                        SELECT code, date, officialLink, title, description FROM Law
                        WHERE code = ?;
                     """;

        try (Connection conn = DataSource.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)
        ){
            st.setString(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(instantiateLaw(rs));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }

        return Optional.empty();
    }

    @Override
    public List<Law> findByBarrierScenario(int barrierScenarioID) {
        String sql = """
                            SELECT BL.law_fk, code, date, officialLink, title, description
                            FROM BarrierScenario_Law as BL
                            JOIN Law on BL.law_fk = Law.code
                            WHERE BL.barrierScenario_fk = ?;
                      """;

        try (Connection conn = DataSource.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)
        ){
            st.setInt(1, barrierScenarioID);
            List<Law> lawList = new ArrayList<>();

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    lawList.add(instantiateLaw(rs));
                }
            }
            return lawList;
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public List<Law> findAll() {
        String sql = """
                        SELECT code, date, officialLink, title, description
                        FROM Law
                    """;
        try (Connection conn = DataSource.getConnection();
            PreparedStatement st = conn.prepareStatement(sql)
        ){
            List<Law> lawList = new ArrayList<>();

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    lawList.add(instantiateLaw(rs));
                }
            }

            return lawList;
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
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
