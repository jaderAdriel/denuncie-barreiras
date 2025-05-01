package com.barreirasapp.service;

import com.barreirasapp.model.Session;
import com.barreirasapp.model.dao.DaoFactory;
import com.barreirasapp.model.dao.SessionDao;
import com.barreirasapp.model.dao.UserDao;
import com.barreirasapp.model.entities.User;
import com.barreirasapp.model.entities.valueobjects.Email;
import com.barreirasapp.utils.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.*;

public class AuthService {

    private final SessionDao sessionRepository;
    private final UserDao userRepository;

    public AuthService() {
        sessionRepository = DaoFactory.createSessionDao();
        userRepository = DaoFactory.createUserDao();
    }

    public void register(User user) {
        userRepository.insert(user);
    }

    public void login(Email email, String password, HttpServletRequest req, HttpServletResponse resp) {
        Optional<User> user = userRepository.getUserByEmail(email);

        if (user.isEmpty()) {
            new ErrorResponse(400, "Usuario não encontrado", null).send(resp);
            return;
        }

        User foundedUser = user.get();

        if (!foundedUser.checkPassword(password)) {
            new ErrorResponse(400, "Senha incorreta", null).send(resp);
            return;
        }

        Session session = new Session(foundedUser);

        sessionRepository.insert(session);

        resp.setHeader("Authorization", session.getSessionId());
    }
}
