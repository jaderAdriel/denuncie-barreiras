package com.barreirasapp.controller;

import com.barreirasapp.annotation.HttpMethod;
import com.barreirasapp.annotation.LoginRequired;
import com.barreirasapp.annotation.Route;
import com.barreirasapp.dto.auth.LoginDTO;
import com.barreirasapp.dto.auth.RegisterUserDTO;
import com.barreirasapp.exceptions.ValidationError;
import com.barreirasapp.service.AuthService;

import com.barreirasapp.utils.ControllerDispatcher;
import com.barreirasapp.utils.routes.RouteInfo;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Logger;


@WebServlet("/accounts/*")
public class AuthController extends HttpServlet {
    private Map<String, RouteInfo> routes;
    private static final Logger LOGGER = Logger.getLogger(AuthController.class.getName());

    private AuthService service;

    @Override
    public void init() throws ServletException {
        this.service = new AuthService();
        System.out.println("configuração de rotas");
        this.routes = Middleware.setupRoutes(this.getClass());
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Middleware.callRoute(this, routes, req, resp);
    }

    @Route(value = "login/", method = HttpMethod.GET_POST)
    public void authenticateUser(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        HttpMethod method = HttpMethod.valueOf(req.getMethod().toUpperCase());
        RequestDispatcher dispatcher = req.getRequestDispatcher("/templates/accounts/login.jsp");

        if (method.equals(HttpMethod.GET)) {
            dispatcher.forward(req, resp);
            return;
        }


        String email = req.getParameter("email");
        req.setAttribute("email", email);
        String password = req.getParameter("password");

        try {
            LoginDTO loginDTO = new LoginDTO(email, password);
            service.login(loginDTO, req, resp);
            resp.sendRedirect("/accounts/protegido/");
        } catch (ValidationError e) {
            ControllerDispatcher.sendErrors(e.getErrors(), req);
            dispatcher.forward(req, resp);
        }
    }

    @Route(value = "register/", method = HttpMethod.GET_POST)
    public void registerUser(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        HttpMethod method = HttpMethod.valueOf(req.getMethod().toUpperCase());
        RequestDispatcher dispatcher = req.getRequestDispatcher("/templates/accounts/register.jsp");

        if (method.equals(HttpMethod.GET)) {
            dispatcher.forward(req, resp);
            return;
        }

        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String password2 = req.getParameter("password2");
        String birthDate = req.getParameter("birthDate");
        String gender = req.getParameter("gender");

        req.setAttribute("name", name);
        req.setAttribute("email", email);
        req.setAttribute("birthDate", birthDate);
        req.setAttribute("gender", gender);

        try {
            RegisterUserDTO registerUserDTO = new RegisterUserDTO(name, email, password2, password, birthDate, gender);
            service.register(registerUserDTO);
            resp.sendRedirect("/accounts/login/");
        } catch (ValidationError e) {
            ControllerDispatcher.sendErrors(e.getErrors(), req);
            dispatcher.forward(req, resp);
        }
    }


    @LoginRequired
    @Route(value = "logout/", method = HttpMethod.GET_POST)
    public void logoutUser(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        service.logout(req, resp);

        resp.sendRedirect("/accounts/login/");
    }

    @LoginRequired
    @Route(value = "protegido/", method = HttpMethod.GET)
    public void showProtectedRouteExample(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain");
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write("Você está autenticado");
    }
}