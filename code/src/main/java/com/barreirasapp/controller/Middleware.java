package com.barreirasapp.controller;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import com.barreirasapp.annotation.HttpMethod;
import com.barreirasapp.annotation.Route;
import com.barreirasapp.infra.proxy.AuthProxy;
import com.barreirasapp.utils.routes.RouteInfo;
import com.barreirasapp.utils.routes.RouteMatchResult;
import com.barreirasapp.utils.routes.RouteParser;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.*;
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

            List<HttpMethod> methods = Arrays.stream(httpMethod.split("_"))
                    .map(String::trim)
                    .map(HttpMethod::valueOf)
                    .toList();

            RouteInfo routeInfo = new RouteInfo(normalizePath(path), methods, method, controllerClass);

            routes.put( httpMethod + ":" + path, routeInfo);
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
        req.setCharacterEncoding("UTF-8");
        Optional<RouteMatchResult> routeMatchResult = findRoute(req, routes);

        if (routeMatchResult.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        RouteInfo routeInfo = routeMatchResult.get().getRouteInfo();

        String methods = routeInfo.toString() + routeInfo.path();

        Map<String, String> params = routeMatchResult.get().getParams();

        params.forEach((key, value) -> {

            String finalValue = value;

            if (value.matches(".*%[0-9A-Fa-f]{2}.*")) {
                finalValue = URLDecoder.decode(value, StandardCharsets.UTF_8);
            }

            req.setAttribute(key, finalValue);
        });



        invokeRoute(controllerInstance, routeInfo.handlerMethod(), req, resp);

    }

    public static Optional<RouteMatchResult> findRoute(HttpServletRequest req, Map<String, RouteInfo> routes) {
        String path = req.getRequestURI();
        String reqMethod = req.getMethod();
        String routeKey = reqMethod + ":" + path ;

        if (routes.containsKey(routeKey)) {
            RouteInfo routeInfo = routes.get(routeKey);
            RouteMatchResult matchResult = RouteParser.matchAndExtract(routeInfo, path, reqMethod);

            return Optional.of(matchResult);
        }

        for (RouteInfo routeInfo : routes.values()) {
            RouteMatchResult matchResult = RouteParser.matchAndExtract(routeInfo, path, reqMethod);

            if (matchResult.isMatch()) {
                return Optional.of(matchResult);
            }
        }

        return routes.values().stream()
                .filter(route -> route.supportsMethod(HttpMethod.valueOf(reqMethod)))
                .map(route -> RouteParser.matchAndExtract(route, path, reqMethod))
                .filter(RouteMatchResult::isMatch)
                .findFirst();
    }

    public static String normalizePath(String path) {
        if (path == null || path.isEmpty()) return "/";
        if (path.charAt(path.length() - 1) == '*') {
            return path.substring(0, path.length() - 1);
        }
        return path;
    }

}
