package com.barreirasapp.repositories.impl;

import com.barreirasapp.infra.db.DataSource;
import com.barreirasapp.infra.db.QueryExecutor;
import com.barreirasapp.infra.exceptions.DatabaseException;
import com.barreirasapp.repositories.LawBarrierScenarioAssociationRepository;
import com.barreirasapp.entities.BarrierScenario;
import com.barreirasapp.entities.Law;

import java.sql.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class LawBarrierScenarioAssociationJDBC implements LawBarrierScenarioAssociationRepository {

    public LawBarrierScenarioAssociationJDBC() {}

    @Override
    public void associate(Law law, BarrierScenario scenario) {
        String sql = """
                          INSERT INTO BarrierScenario_Law (barrierScenario_fk, law_fk)
                          VALUES (?, ?);
                      """;
        try (Connection conn = DataSource.getConnection();
            PreparedStatement st = conn.prepareStatement(sql)
        ) {

            st.setInt( 1, scenario.getId());
            st.setString( 2 , law.getCode());
            QueryExecutor.executeUpdate(st, conn);

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public Set<Law> findByBarrierScenarioId(int barrierScenarioID) {
        String sql = """
                        SELECT BL.law_fk, code, date, officialLink, title, description
                        FROM BarrierScenario_Law as BL
                        JOIN Law on BL.law_fk = Law.code
                        WHERE BL.barrierScenario_fk = ?;
                     """;
        try (Connection conn = DataSource.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)
        ) {

            st.setInt(1, barrierScenarioID);
            Set<Law> lawList = new HashSet<>();

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    String lawCode = rs.getString("code");
                    String lawTitle = rs.getString("title");
                    String lawDescription = rs.getString("description");
                    String lawOfficialLink = rs.getString("officialLink");
                    LocalDate lawDate = LocalDate.parse(rs.getString("date"));
                    lawList.add(new Law(lawCode, lawDate, lawOfficialLink, lawTitle, lawDescription));
                }
            }

            return lawList;

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public void dissociate(BarrierScenario scenario, Law law) {
        String sql = """
                          DELETE FROM BarrierScenario_Law
                          WHERE law_fk = ? AND barrierScenario_fk = ?
                      """;
        try (Connection conn = DataSource.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)
        ) {

            st.setString( 1 , law.getCode());
            st.setInt( 2 , scenario.getId());

            QueryExecutor.executeUpdate(st, conn);

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public void persistAssociatedLaws(BarrierScenario scenario) {
        Set<Law> associatedLaws = findByBarrierScenarioId(scenario.getId());

        Set<Law> associatedLawsToUpdate = scenario.getAssociatedLaws();

        for (Law law : associatedLawsToUpdate) {
            if (associatedLaws.contains(law)) continue;
            associate(law, scenario);
        }

        for (Law law : associatedLaws) {
            if (associatedLawsToUpdate.contains(law)) continue;
            dissociate(scenario, law);
        }
    }
}

