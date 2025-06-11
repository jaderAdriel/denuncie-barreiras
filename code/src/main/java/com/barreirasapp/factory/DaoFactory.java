package com.barreirasapp.factory;

import com.barreirasapp.infra.db.DataSource;
import com.barreirasapp.repositories.*;
import com.barreirasapp.repositories.impl.*;

import java.sql.Connection;

public class DaoFactory {


    public UserRepository createUserDao() {
        return new UserRepositoryJDBC();
    }

    public ReportRepository createReportDao() {
        return new ReportRepositoryJDBC(this.createUserDao());
    }

    public EntityRepository createEntityDao() {
        return new EntityRepositoryJDBC(this.createUserDao());
    }

    public SessionRepository createSessionDao() {
        return new SessionRepositoryJDBC(this.createUserDao());
    }

    public LawRepository createLawDao() {
        return new LawRepositoryJDBC();
    }

    public CommentRepository createCommentDao() {
        return new CommentRepositoryJBDC(this.createUserDao());
    }

    public BarrierScenarioLikeRepository createBarrierScenarioLikeDao() {
        return new BarrierScenarioLikeRepositoryJBDC(this.createUserDao());
    }

    public FileRepository createFileDao() {
        return new FileRepositoryLocal();
    }

    public LawBarrierScenarioAssociationRepository createLawBarrierScenarioAssociationDao() {
        return new LawBarrierScenarioAssociationJDBC();
    }

    public BarrierScenarioRepository createBarrierScenario() {
        return new BarrierScenarioRepositoryJDBC(
                this.createCommentDao(),
                this.createLawBarrierScenarioAssociationDao(),
                this.createBarrierScenarioLikeDao(),
                this.createUserDao()
        );
    }
}
