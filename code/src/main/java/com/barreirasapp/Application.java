package com.barreirasapp;

import com.barreirasapp.infra.proxy.RouteProxy;
import io.javalin.Javalin;

public class Application {
    public static void main(String[] args) {

        var app = Javalin.create().start(7070);

        RouteProxy.registerRoutes(app);
    }
}
