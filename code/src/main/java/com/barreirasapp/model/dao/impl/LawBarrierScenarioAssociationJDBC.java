package com.barreirasapp.model.dao.impl;

import com.barreirasapp.infra.db.DatabaseConnection;
import com.barreirasapp.infra.exceptions.DatabaseException;
import com.barreirasapp.model.dao.LawBarrierScenarioAssociationDao;
import com.barreirasapp.model.entities.BarrierScenario;
import com.barreirasapp.model.entities.Law;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LawBarrierScenarioAssociationJDBC implements LawBarrierScenarioAssociationDao {

    private final Connection conn;

    public LawBarrierScenarioAssociationJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void associate(Law law, BarrierScenario scenario) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    """
                              INSERT INTO BarrierScenario_Law (barrierScenario_fk, law_fk)
                              VALUES (?, ?);
                      """, Statement.RETURN_GENERATED_KEYS
            );

            st.setInt( 1, scenario.getId());
            st.setString( 2 , law.getCode());

            st.execute();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            DatabaseConnection.closeStatement(st);
        }
    }

    @Override
    public void dissociate(BarrierScenario scenario, Law law) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    """
                              DELETE FROM BarrierScenario_Law
                              WHERE law_fk = ? AND barrierScenario_fk = ?
                      """, Statement.RETURN_GENERATED_KEYS
            );
            st.setString( 1 , law.getCode());
            st.setInt( 2 , scenario.getId());

            st.execute();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            DatabaseConnection.closeStatement(st);
        }
    }

    @Override
    public void update(BarrierScenario scenario) {
        List<Law> associatedLaws = findByBarrierScenarioId(scenario.getId());

        List<Law> associatedLawsToUpdate = scenario.getAssociatedLaws();

        for (Law law : associatedLawsToUpdate) {
            if (associatedLaws.contains(law)) continue;
            associate(law, scenario);
        }

        for (Law law : associatedLaws) {
            if (associatedLawsToUpdate.contains(law)) continue;
            dissociate(scenario, law);
        }
    }

    @Override
    public List<Law> findByBarrierScenarioId(int barrierScenarioID) {
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
                String lawCode = rs.getString("code");
                String lawTitle = rs.getString("title");
                String lawDescription = rs.getString("description");
                String lawOfficialLink = rs.getString("officialLink");
                LocalDate lawDate = LocalDate.parse(rs.getString("date"));
                lawList.add(new Law(lawCode, lawDate, lawOfficialLink, lawTitle, lawDescription));
            }

            return lawList;

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            DatabaseConnection.closeResultSet(rs);
            DatabaseConnection.closeStatement(st);
        }
    }
}

