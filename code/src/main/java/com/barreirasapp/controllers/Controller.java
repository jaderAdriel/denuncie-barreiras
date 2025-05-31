package com.barreirasapp.controllers;

import com.barreirasapp.factory.DaoFactory;
import com.barreirasapp.factory.ServiceFactory;
import com.barreirasapp.infra.Middleware;
import com.barreirasapp.utils.routes.RouteInfo;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

public abstract class Controller extends HttpServlet {
    private Map<String, RouteInfo> routes;
    private ServiceFactory serviceFactory;
    private DaoFactory daoFactory;
    @Override
    public void init() throws ServletException {
        this.routes = Middleware.setupRoutes(this.getClass());
        this.serviceFactory = (ServiceFactory) getServletContext().getAttribute("serviceFactory");
        this.daoFactory = (DaoFactory) getServletContext().getAttribute("daoFactory");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Middleware.callRoute(this, routes, req, resp);
    }

    public ServiceFactory getServiceFactory() {
        return this.serviceFactory;
    }

    public DaoFactory getDaoFactory() {
        return daoFactory;
    }
}
