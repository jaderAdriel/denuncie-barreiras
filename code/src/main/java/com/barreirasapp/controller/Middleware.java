package com.barreirasapp.controller;

import com.barreirasapp.annotation.Route;
import com.barreirasapp.infra.proxy.AuthProxy;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Middleware {

    private static final Logger LOGGER = Logger.getLogger(Middleware.class.getName());

    public static Map<String, Method> setupRoutes (Class<?> controllerClass) {
        Map<String, Method> routes = new HashMap<>();

        if (!controllerClass.isAnnotationPresent(WebServlet.class)) {
            LOGGER.warning("Classe " + controllerClass.getSimpleName() + " não está marcada como @WebServlet");
            return routes;
        }

        WebServlet controllerAnnotation = controllerClass.getAnnotation(WebServlet.class);
        System.out.println("A classe " + controllerClass.getName() + "está sendo mapeado");

        String endpoint = normalizePath(controllerAnnotation.value()[0]);
        System.out.println("configuração endpoint" + endpoint);

        for (Method method : controllerClass.getDeclaredMethods()) {
            System.out.println("A classe " + controllerClass.getName() + " método " + method.getName() + "corresponde a uma rota");

            if (!method.isAnnotationPresent(Route.class)) continue;

            System.out.println("o método " + method.getName() + "está sendo mapeado");

            Route route = method.getAnnotation(Route.class);

            String path = endpoint + route.value();
            String httpMethod = route.method().toString().toUpperCase();
            LOGGER.warning(path + ":" +  httpMethod);

            routes.put(path + ":" +  httpMethod, method);
            System.out.println("configuração endpoint " + path + ":" +  httpMethod);

        }

        return routes;
    }

    public static void invokeRoute(Object controllerInstance, Method method, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            AuthProxy.invoke(controllerInstance, method, req, resp);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Erro ao invocar rota: " + method.getName(), e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro interno no servidor");
        }
    }

    public static void callRoute(Object controllerInstance, Map<String, Method> routes, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getRequestURI();
        String method = req.getMethod();
        String routeKey = path + ":" + method;

        System.out.println("Uma requisição foi feita: " + method +  " " + path + "  "  + routeKey);

        Method handlerMethod = routes.get(routeKey);
        if (handlerMethod != null) {
            invokeRoute(controllerInstance, handlerMethod, req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    public static String normalizePath(String path) {
        if (path == null || path.isEmpty()) return "/";
        if (path.charAt(path.length() - 1) == '*') {
            return path.substring(0, path.length() - 1);
        }
        return path;
    }

    private static void sendJsonError(HttpServletResponse resp, int status,
                                      String error, String message) throws IOException {
        resp.setContentType("application/json");
        resp.setStatus(status);
        resp.getWriter().write(String.format(
                "{\"error\": \"%s\", \"message\": \"%s\"}",
                error, message
        ));
    }


}
