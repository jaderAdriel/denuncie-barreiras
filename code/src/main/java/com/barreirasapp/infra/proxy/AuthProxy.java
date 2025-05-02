package com.barreirasapp.infra.proxy;

import com.barreirasapp.annotation.LoginRequired;
import com.barreirasapp.infra.security.UserContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.lang.reflect.Method;

public class AuthProxy {

    public static void invoke(Object target, Method method, HttpServletRequest req, HttpServletResponse resp) {
        try {
            String sessionId = UserContext.getSessionId(req.getCookies());

            if (method.isAnnotationPresent(LoginRequired.class) && !UserContext.isUserAuthenticated(sessionId)) {
                System.out.println("Usuário tentou acessar " + req.getRequestURI() + " mas não está logado, redirecionando para tela de login");
                resp.sendRedirect("/accounts/login/");
                // Aqui mais pra frente deve implementar algo como? "/accounts/redirect?next=" + req.getRequestURI() para
                // redirecionar a página que o usuário queria acessar antes de fazer login
                return;
            }

            method.invoke(target, req, resp);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao chamar método: " + e.getMessage());
        }
    }
}
