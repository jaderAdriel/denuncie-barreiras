package com.barreirasapp.infra.security;

import com.barreirasapp.model.Session;
import com.barreirasapp.model.dao.DaoFactory;
import com.barreirasapp.model.dao.SessionDao;
import com.barreirasapp.model.dao.UserDao;
import com.barreirasapp.model.entities.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

public class UserContext {

    public static String getSessionId(Cookie[] cookies) {
        if (cookies == null) return null;

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("sessionId")) {
                return cookie.getValue();
            }
        }

        return null;
    }

    public static boolean isUserAuthenticated(String sessionId) {
        if (sessionId == null) return false;

        SessionDao sessionRepository = DaoFactory.createSessionDao();
        Optional<Session> session = sessionRepository.findByid(sessionId);

        return session.isPresent();
    }

    public static Optional<User> getUserFromSession(String sessionId) {

        SessionDao sessionRepository = DaoFactory.createSessionDao();
        Optional<Session> session = sessionRepository.findByid(sessionId);

        if (session.isEmpty()) {
            return Optional.empty();
        }
        UserDao userRepository = DaoFactory.createUserDao();
        Integer userId = session.get().getUserId();

        return Optional.of(userRepository.findById(userId));
    }

    public static Session createSession(User user) {
        SessionDao sessionRepository = DaoFactory.createSessionDao();
        sessionRepository.deleteAllByUser(user);

        Session session = new Session(user);
        sessionRepository.insert(session);

        return session;
    }

    public static void removeSession(String sessionId) {
        SessionDao sessionRepository = DaoFactory.createSessionDao();
        sessionRepository.deleteById(sessionId);
    }
}
