package com.barreirasapp.controller;

import com.barreirasapp.annotation.HttpMethod;
import com.barreirasapp.annotation.Route;
import com.barreirasapp.model.entities.User;
import com.barreirasapp.model.entities.valueobjects.Email;
import com.barreirasapp.model.enums.Gender;
import com.barreirasapp.service.AuthService;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.Map;
import java.util.logging.Logger;


@WebServlet("/accounts/*")
public class AuthController extends HttpServlet {
    private Map<String, Method> routes;
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
        System.out.println("configuração de middleware");
        Middleware.callRoute(this, routes, req, resp);
    }

    @Route(value = "login/", method = HttpMethod.POST)
    public void handleLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            sendJsonResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                    Map.of("error", "Email e senha são obrigatórios"));
            return;
        }
        service.login(new Email(email), password, req, resp);
    }

    @Route(value = "register/", method = HttpMethod.POST)
    public void handleRegister(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String birthDate = req.getParameter("birthDate");
        String gender = req.getParameter("gender");

        if (name == null || email == null || password == null ||
                birthDate == null || gender == null) {
            sendJsonResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                    Map.of("error", "Todos os campos são obrigatórios"));
            return;
        }

        User newUser = new User();
        newUser.setName(name);
        newUser.setPassword(password);
        newUser.setEmail(new Email(email));
        newUser.setBirthDate(LocalDate.parse(birthDate));
        newUser.setGender(Gender.valueOf(gender.toUpperCase()));

        service.register(newUser);
    }

    @Route(value = "protegido/", method = HttpMethod.POST)
    public void handleProtegido(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain");
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write("Você acessou uma rota protegida!");
    }

    @Route(value = "publico/", method = HttpMethod.POST)
    public void handlePublico(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain");
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write("Você acessou uma rota publica!");
    }

    private void sendJsonResponse(HttpServletResponse resp, int status, Map<String, String> data) throws IOException {
        resp.setContentType("application/json");
        resp.setStatus(status);
        resp.getWriter().write(new Gson().toJson(data));
    }
}