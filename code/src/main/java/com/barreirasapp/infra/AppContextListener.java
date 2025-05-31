package com.barreirasapp.infra;

import com.barreirasapp.factory.DaoFactory;
import com.barreirasapp.factory.ServiceFactory;
import com.barreirasapp.infra.db.DataSource;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.sql.Connection;

@WebListener
public class AppContextListener implements ServletContextListener {
    private DaoFactory daoFactory;
    private ServiceFactory serviceFactory;

    // Essa função é chamada quando iniciamos a aplicação
    @Override
    public void contextInitialized(ServletContextEvent contextEvent) {
        System.out.println("Olá, a aplicação foi iniciada");
        try {
            this.daoFactory = new DaoFactory();
            this.serviceFactory = new ServiceFactory(this.daoFactory);

            contextEvent.getServletContext().setAttribute("serviceFactory", serviceFactory);
            contextEvent.getServletContext().setAttribute("daoFactory", daoFactory);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao inicializar a dependências", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent contextEvent) {
        System.out.println("Tchau, a aplicação está sendo finalizada");
        DataSource.close();
    }
}
