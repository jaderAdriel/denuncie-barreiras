package com.barreirasapp.infra.proxy;

import com.barreirasapp.annotation.LoginRequired;
import com.barreirasapp.exceptions.ValidationError;
import com.barreirasapp.infra.security.UserContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.ServerException;

public class AuthProxy {

    public static void invoke(Object target, Method method, HttpServletRequest req, HttpServletResponse resp) throws InvocationTargetException, IOException, IllegalAccessException {

        String sessionId = UserContext.getSessionId(req.getCookies());

        boolean isUserAuthenticated = UserContext.isUserAuthenticated(sessionId);

        req.setAttribute("isUserAuthenticated", isUserAuthenticated);

        if (method.isAnnotationPresent(LoginRequired.class) && !isUserAuthenticated) {
            resp.sendRedirect("/accounts/login/?next=" + req.getRequestURI());
            return;
        }

        method.invoke(target, req, resp);
    }
}
