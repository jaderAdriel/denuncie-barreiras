package com.barreirasapp.service;

import com.barreirasapp.model.Session;
import com.barreirasapp.model.dao.DaoFactory;
import com.barreirasapp.model.dao.SessionDao;
import com.barreirasapp.model.dao.UserDao;
import com.barreirasapp.model.entities.User;
import com.barreirasapp.model.entities.valueobjects.Email;
import io.javalin.http.Context;

import java.util.*;

public class AuthService {

    private final SessionDao sessionRepository;
    private final UserDao userRepository;

    public AuthService() {
        sessionRepository = DaoFactory.createSessionDao();
        userRepository = DaoFactory.createUserDao();
    }

    public void register(User user) {
        System.out.println("Entrou em service");
        userRepository.insert(user);
    }

    public void login(Email email, String password, Context context) {
        Optional<User> user = userRepository.getUserByEmail(email);

        if (user.isEmpty()) {
            context.result("Usuario não encontrado");
            context.res().setStatus(400);
            return;
        }

        User foundedUser = user.get();

        if (!foundedUser.checkPassword(password)) {
            context.result("Senhão não batem");
            context.res().setStatus(400);
            return;
        }

        String sessionId = UUID.randomUUID().toString();
        Session session = new Session(sessionId, foundedUser);

        sessionRepository.insert(session);


        context.cookie("Authorization", sessionId);
    }
}
