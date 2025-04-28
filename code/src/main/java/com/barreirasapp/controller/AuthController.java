package com.barreirasapp.controller;

import com.barreirasapp.annotation.ControllerRoute;
import com.barreirasapp.annotation.HttpMethod;
import com.barreirasapp.annotation.LoginRequired;
import com.barreirasapp.annotation.Route;
import com.barreirasapp.dto.LoginDTO;
import com.barreirasapp.dto.RegisterDTO;
import com.barreirasapp.model.entities.User;
import com.barreirasapp.model.entities.valueobjects.Email;
import com.barreirasapp.model.enums.Gender;
import com.barreirasapp.service.AuthService;
import io.javalin.http.Context;

import java.time.LocalDate;
import java.util.Map;

@ControllerRoute("/api/accounts/")
public class AuthController {

    AuthService service;

    public AuthController() {
        this.service = new AuthService();
    }

    @Route(value="login/", method= HttpMethod.POST)
    public void login(Context ctx) {
        // Obtém os parâmetros do formulário
        String email = ctx.formParam("email");
        String password = ctx.formParam("password");

        // Validação manual
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            ctx.status(400).json(Map.of("error", "Email e senha são obrigatórios"));
            return;
        }

        service.login(new Email(email), password, ctx);
    }

    @Route(value="register/", method= HttpMethod.POST)
    public void register(Context ctx) {
        // Obtém os parâmetros do formulário
        String name = ctx.formParam("name");
        String email = ctx.formParam("email");
        String password = ctx.formParam("password");
        String birthDate = ctx.formParam("birthDate");
        String gender = ctx.formParam("gender");

        // Validação manual
        if (name == null || email == null || password == null ||
                birthDate == null || gender == null) {
            ctx.status(400).json(Map.of("error", "Todos os campos são obrigatórios"));
            return;
        }

        // Cria o usuário
        User newUser = new User();
        newUser.setName(name);
        newUser.setPassword(password); // (IMPORTANTE: Hash no serviço!)
        newUser.setEmail(new Email(email));
        newUser.setBirthDate(LocalDate.parse(birthDate));
        newUser.setGender(Gender.valueOf(gender.toUpperCase()));

        service.register(newUser);
    }

    @LoginRequired
    @Route(value="protegido/", method= HttpMethod.GET)
    public void teste1(Context ctx) {
        ctx.result("Você acessou uma rota protegida!");
        ctx.res().setStatus(200);
    }

    @Route(value="publico/", method= HttpMethod.GET)
    public void teste2(Context ctx) {
        ctx.result("Você acessou uma rota publica!");
        ctx.res().setStatus(200);
    }
}
