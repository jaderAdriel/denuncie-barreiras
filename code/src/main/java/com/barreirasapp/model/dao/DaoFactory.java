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
    public static CommentDao createCommentDao() {
        return new CommentDaoJBDC(DatabaseConnection.getConnection());
    }
    public static BarrierScenarioLikeDao createBarrierScenarioLikeDao() {
        return new BarrierScenarioLikeDaoJBDC(
                DatabaseConnection.getConnection(),
                DaoFactory.createUserDao()
        );
    }

    public static FileDao createFileDao() {
        return new FileDaoLocal();
    }

    public static LawBarrierScenarioAssociationDao createLawBarrierScenarioAssociationDao() {
        return new LawBarrierScenarioAssociationJDBC(DatabaseConnection.getConnection());
    }
    public static BarrierScenarioDao createBarrierScenario() {
        return new BarrierScenarioDaoJDBC(
                DatabaseConnection.getConnection(),
                DaoFactory.createCommentDao(),
                DaoFactory.createLawBarrierScenarioAssociationDao(),
                DaoFactory.createBarrierScenarioLikeDao(),
                DaoFactory.createUserDao()
        );
    }
}
