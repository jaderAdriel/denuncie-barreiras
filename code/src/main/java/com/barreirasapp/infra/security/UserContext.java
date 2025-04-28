package com.barreirasapp.infra.security;

import com.barreirasapp.model.Session;
import com.barreirasapp.model.dao.DaoFactory;
import com.barreirasapp.model.dao.SessionDao;

import java.util.Objects;
import java.util.Optional;

public class UserContext {

    public static boolean isUserAuthenticated(String sessionId) {

        SessionDao dao = DaoFactory.createSessionDao();
        Optional<Session> session = dao.findByid(sessionId);
        System.out.println("Session id is not present? " + sessionId.isEmpty());
        return session.isPresent();
    }
}
