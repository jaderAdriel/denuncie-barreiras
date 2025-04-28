package com.barreirasapp.infra.proxy;

import com.barreirasapp.annotation.ControllerRoute;
import com.barreirasapp.annotation.HttpMethod;
import com.barreirasapp.annotation.Route;
import com.barreirasapp.controller.AuthController;
import io.javalin.Javalin;

import java.lang.reflect.Method;
import java.util.Set;

public class RouteProxy {

    public static void registerRoutes(Javalin app) {

        Set<Class<?>> controllerClasses = scanForControllerClasses("");

        for (Class<?> classe : controllerClasses) {
            registerClassRoutes(classe, app);
        }

    }

    public static void registerClassRoutes(Class<?> controllerClass, Javalin app) {
        ControllerRoute controllerRouteClassAnnotation = controllerClass.getAnnotation(ControllerRoute.class);
        String endpoint = controllerRouteClassAnnotation.value();

        for (Method method : controllerClass.getMethods()) {
            System.out.println(method.getName());
            if (!method.isAnnotationPresent(Route.class)) continue;

            Route route = method.getAnnotation(Route.class);

            String path = endpoint + route.value();
            HttpMethod httpMethod = route.method();

            try {
                // Cria uma nova instância do controlador
                Object controllerInstance = controllerClass.getDeclaredConstructor().newInstance();

                // Registra a rota no Javalin dependendo do método HTTP
                switch (httpMethod) {
                    case GET:
                            System.out.println("get " + path);

                            app.get(path, ctx ->
                                    AuthProxy.invoke(controllerInstance, method, ctx)
                            );
                        break;
                    case POST:
                        app.post(path, ctx ->
                                AuthProxy.invoke(controllerInstance, method, ctx)
                        );
                        break;
                    case PUT:
                        app.put(path, ctx ->
                                AuthProxy.invoke(controllerInstance, method, ctx)
                        );
                        break;
                    case DELETE:
                        app.delete(path, ctx ->
                                AuthProxy.invoke(controllerInstance, method, ctx)
                        );
                        break;
                    default:
                        throw new UnsupportedOperationException("HTTP method not supported");
                }
                System.out.println("Rota " + httpMethod + " " + path );

            } catch (Exception e) {
                throw new RuntimeException("Something went wrong: " + e.getMessage());
            }


        }
    }

    public static Set<Class<?>> scanForControllerClasses(String packageName) {
        return Set.of(AuthController.class);
    }
}
