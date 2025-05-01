package com.barreirasapp.infra.proxy;

import com.barreirasapp.annotation.LoginRequired;
import com.barreirasapp.infra.security.UserContext;
import com.barreirasapp.utils.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.lang.reflect.Method;

public class AuthProxy {

    public static void invoke(Object target, Method method, HttpServletRequest req, HttpServletResponse resp) {
        try {
            String sessionId =  req.getHeader("Authorization");
            System.out.println("AuthProxy - session_id : " + sessionId);

            if (method.isAnnotationPresent(LoginRequired.class) && !UserContext.isUserAuthenticated(sessionId)) {
                ErrorResponse err = new ErrorResponse(401, "Usuário não logado", "/accounts/login");
                err.send(resp);
                return;
            }
            method.invoke(target, req, resp);
        } catch (Exception e) {
            System.out.println("Erro ao chamar método: " + e.getMessage());
        }
    }
}
