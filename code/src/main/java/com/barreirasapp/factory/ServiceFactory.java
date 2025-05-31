package com.barreirasapp.factory;

import com.barreirasapp.services.AuthService;
import com.barreirasapp.services.BarrierScenarioService;
import com.barreirasapp.services.LawService;
import com.barreirasapp.services.ReportService;

public class ServiceFactory {
    private final DaoFactory daoFactory;

    public ServiceFactory(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public AuthService getAuthService() {
      return new AuthService(daoFactory.createUserDao(), daoFactory.createSessionDao());
    }

    public LawService getLawService() {
        return new LawService(daoFactory.createLawDao());
    }

    public BarrierScenarioService getBarrierScenarioService() {
        return new BarrierScenarioService(
                daoFactory.createBarrierScenario(),
                daoFactory.createFileDao(),
                this.getLawService()
        );
    }

    public ReportService getReportService() {
        return new ReportService(daoFactory.createReportDao(), this.getBarrierScenarioService());
    }
}
