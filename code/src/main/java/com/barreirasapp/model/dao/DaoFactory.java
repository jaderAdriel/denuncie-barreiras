package com.barreirasapp.model.dao;

import com.barreirasapp.infra.db.DatabaseConnection;
import com.barreirasapp.model.dao.impl.*;

public class DaoFactory {
    public static UserDao createUserDao() {
        return new UserDaoJDBC(DatabaseConnection.getConnection());
    }
    public static ReportDao createReportDao() {
        return new ReportDaoJDBC(DatabaseConnection.getConnection());
    }
    public static SessionDao createSessionDao() {
        return new SessionDaoJDBC(DatabaseConnection.getConnection());
    }
    public static LawDao createLawDao() {
        return new LawDaoJDBC(DatabaseConnection.getConnection());
    }
    public static BarrierScenarioDao createBarrierScenario() {
        return new BarrierScenarioDaoJDBC(DatabaseConnection.getConnection());
    }
}
