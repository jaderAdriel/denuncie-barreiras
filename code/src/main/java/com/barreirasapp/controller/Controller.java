package com.barreirasapp.controller;

import com.barreirasapp.utils.Middleware;
import com.barreirasapp.utils.routes.RouteInfo;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

public abstract class Controller extends HttpServlet {
    private Map<String, RouteInfo> routes;

    @Override
    public void init() throws ServletException {
        this.routes = Middleware.setupRoutes(this.getClass());
    }


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Middleware.callRoute(this, routes, req, resp);
    }
}
