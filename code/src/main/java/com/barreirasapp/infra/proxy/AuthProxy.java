package com.barreirasapp.infra.proxy;

import com.barreirasapp.annotation.LoginRequired;
import com.barreirasapp.infra.security.UserContext;
import io.javalin.http.Context;

import java.lang.reflect.Method;

public class AuthProxy {

    public static void invoke(Object target, Method method, Context ctx) {
        try {
            String cookieId = ctx.header("Authorization");
            System.out.println("AuthProxy - cookieId : " + cookieId);

            if (!method.isAnnotationPresent(LoginRequired.class)) {
                System.out.println("AuthProxy: A URI " + ctx.req().getRequestURI() + "Não está protegida");
                method.invoke(target, ctx);
                return;
            }

            if (!UserContext.isUserAuthenticated(cookieId)) {
                ctx.res().setStatus(401);
                return;
            }

            method.invoke(target, ctx);

        } catch (Exception e) {
            System.out.println("Erro ao chamar metodo");
        }
    }
}
