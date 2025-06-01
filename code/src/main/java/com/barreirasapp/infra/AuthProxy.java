package com.barreirasapp.infra;

import com.barreirasapp.infra.annotation.HasRole;
import com.barreirasapp.infra.annotation.LoginRequired;
import com.barreirasapp.factory.ServiceFactory;
import com.barreirasapp.entities.Session;
import com.barreirasapp.entities.User;
import com.barreirasapp.entities.enums.UserRole;
import com.barreirasapp.services.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;

public class AuthProxy {

    public static void invoke(Object target, Method method, HttpServletRequest req, HttpServletResponse resp) throws InvocationTargetException, IOException, IllegalAccessException {

        ServiceFactory serviceFactory = (ServiceFactory) req.getServletContext().getAttribute("serviceFactory");
        AuthService authService = serviceFactory.getAuthService();

        // Pega os cookies da requisição
        Cookie[] cookies = req.getCookies();

        // Pega o id da sessão através dos cookies da requisição
        String sessionId = authService.getSessionId(cookies);

        // Busca a sessão salva a partir do id da sessão informada
        Optional<Session> session = authService.getSessionById(sessionId);

        // guarda boleano se existe sessão
        boolean isUserAuthenticated = session.isPresent();

        req.setAttribute("isUserAuthenticated", isUserAuthenticated);

        // Verifica se o método precisa ter login para ser acessado e se o usuário está logado
        if (method.isAnnotationPresent(LoginRequired.class) && !isUserAuthenticated) {
            resp.sendRedirect("/accounts/login/?next=" + req.getRequestURI());
            return;
        }

        Optional<User> userOptional = authService.getUserFromSession(sessionId);

        req.setAttribute("isAuthenticated", userOptional.isPresent());
        userOptional.ifPresent((user) -> req.setAttribute("user", user));

        HasRole roleAnnotation = method.getAnnotation(HasRole.class);
        if (method.isAnnotationPresent(HasRole.class) && roleAnnotation != null) {
            UserRole roleNeeded = roleAnnotation.value();
            User user = userOptional.orElseThrow();

            if (roleNeeded != user.getRole()) {
                resp.sendRedirect("/accounts/login/?next=" + req.getRequestURI());
                return;
            }
        }

        method.invoke(target, req, resp);
    }
}
