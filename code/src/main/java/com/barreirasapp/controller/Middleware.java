package com.barreirasapp.controller;

import com.barreirasapp.annotation.Route;
import com.barreirasapp.infra.proxy.AuthProxy;
import com.barreirasapp.utils.Route.RouteInfo;
import com.barreirasapp.utils.Route.RouteMatchResult;
import com.barreirasapp.utils.Route.RouteParser;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Middleware {

    private static final Logger LOGGER = Logger.getLogger(Middleware.class.getName());

    //    /law/index/:GET -> Method
    //    /law/create/:POST -> Method
    public static Map<String, RouteInfo> setupRoutes (Class<?> controllerClass) {
        Map<String, RouteInfo> routes = new HashMap<>();

        if (!controllerClass.isAnnotationPresent(WebServlet.class)) {
            LOGGER.warning("Classe " + controllerClass.getSimpleName() + " não está marcada como @WebServlet");
            return routes;
        }

        WebServlet controllerAnnotation = controllerClass.getAnnotation(WebServlet.class);
        System.out.println("A classe " + controllerClass.getSimpleName() + " está sendo mapeado");

        String endpoint = normalizePath(controllerAnnotation.value()[0]);
        System.out.println("configuração endpoint " + endpoint);

        for (Method method : controllerClass.getDeclaredMethods()) {
            System.out.println("A classe " + controllerClass.getName() + " método " + method.getName() + " corresponde a uma rota");

            if (!method.isAnnotationPresent(Route.class)) continue;

            System.out.println("o método " + method.getName() + " está sendo mapeado");

            Route route = method.getAnnotation(Route.class);

            String path = endpoint + route.value();
            String httpMethod = route.method().toString().toUpperCase();

            RouteInfo routeInfo = new RouteInfo(normalizePath(path), route.method(), method, controllerClass);

            routes.put(path + ":" +  httpMethod, routeInfo);
            System.out.println("configuração endpoint " + path + " : " +  httpMethod);
        }

        return routes;
    }

    public static void invokeRoute(Object controllerInstance, Method method, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            AuthProxy.invoke(controllerInstance, method, req, resp);
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro interno no servidor");
        }
    }

    public static void callRoute(Object controllerInstance, Map<String, RouteInfo> routes, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getRequestURI();
        String method = req.getMethod();
        String routeKey = path + ":" + method ;

        System.out.println("Uma requisição foi feita para: " + routeKey);

        RouteInfo route = routes.get(routeKey);

        if (!routes.containsKey(routeKey)) {
            Optional<RouteInfo> findResult = findRoute(path, routes);

            if (findResult.isPresent()) {
                route = findResult.get();
            }
        }

        if (route != null) {
            invokeRoute(controllerInstance, route.handlerMethod(), req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    public static Optional<RouteInfo> findRoute(String path, Map<String, RouteInfo> routes) {
        for (RouteInfo route : routes.values()) {
            RouteMatchResult matchResult = RouteParser.matchAndExtract(route.path(), path);

            if (matchResult.isMatch())
                System.out.println("Foi achado um método para " + path );
                return Optional.of(route);
        }
        return Optional.empty();
    }

    public static String normalizePath(String path) {
        if (path == null || path.isEmpty()) return "/";
        if (path.charAt(path.length() - 1) == '*') {
            return path.substring(0, path.length() - 1);
        }
        return path;
    }

}
