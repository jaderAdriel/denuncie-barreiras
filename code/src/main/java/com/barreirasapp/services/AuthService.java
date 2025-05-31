package com.barreirasapp.services;

import com.barreirasapp.repositories.SessionRepository;
import com.barreirasapp.dto.auth.LoginDTO;
import com.barreirasapp.dto.auth.RegisterUserDTO;
import com.barreirasapp.exceptions.ValidationError;
import com.barreirasapp.entities.Session;
import com.barreirasapp.repositories.UserRepository;
import com.barreirasapp.entities.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.*;

public class AuthService {

    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;

    public AuthService(UserRepository userRepository, SessionRepository sessionRepository) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
    }

    public void register(RegisterUserDTO registerUserDTO) throws ValidationError {

        Optional<User> existentUser = userRepository.findByEmail(registerUserDTO.getEmail());

        if (existentUser.isPresent()) {
            throw new ValidationError("Erro de validação", Map.of("error", "Já existe um usuário com esse email"));
        }

        User user = new User(
                registerUserDTO.getName(),
                registerUserDTO.getEmail(),
                registerUserDTO.getBirthDate(),
                registerUserDTO.getGender(),
                registerUserDTO.getPassword()
        );

        userRepository.insert(user);
    }

    public void logout(HttpServletRequest req, HttpServletResponse resp) {
        String sessionId = this.getSessionId(req.getCookies());
        sessionRepository.deleteById(sessionId);
        this.clearAllCookies(resp, req);
    }

    public void login(LoginDTO loginDTO, HttpServletRequest req, HttpServletResponse resp) throws ValidationError {
        User foundedUser = userRepository.findByEmail(loginDTO.email)
                .orElseThrow(() ->  new ValidationError( "" ,Map.of("email", "Usuário não encontrado")));

        if (!foundedUser.checkPassword(loginDTO.password)) {
            throw new ValidationError( "" ,Map.of("password", "Senha incorreta"));
        }

        sessionRepository.deleteAllByUser(foundedUser);
        Session session = new Session(foundedUser);
        sessionRepository.insert(session);

        this.addSessionCookies(resp, session);
    }

    public String getSessionId(Cookie[] cookies) {
        return getCookieValue(cookies, "sessionId");
    }

    public String getCookieValue(Cookie[] cookies, String cookieName) {
        if (cookies == null) return null;

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(cookieName)) {
                return cookie.getValue();
            }
        }
        return null;
    }

    public Optional<Session> getSessionById(String sessionId) {
        return sessionRepository.findById(sessionId);
    }

    public void clearAllCookies(HttpServletResponse resp, HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();

        for (Cookie cookie : cookies) {
            cookie.setMaxAge(0);
            resp.addCookie(cookie);
        }
    }

    public void addSessionCookies(HttpServletResponse resp, Session session) {
        Cookie sessionCookie = new Cookie("sessionId", session.getSessionId());
        sessionCookie.setPath("/");
        sessionCookie.setHttpOnly(true);
        resp.addCookie(sessionCookie);

        Cookie userName = new Cookie("username", session.getUser().getName());
        userName.setPath("/");
        userName.setHttpOnly(true);
        resp.addCookie(sessionCookie);

        Cookie userCookie = new Cookie("userId", String.valueOf(session.getUserId()));
        userCookie.setPath("/");
        userCookie.setHttpOnly(true);
        resp.addCookie(userCookie);
    }

    public Optional<User> getUserFromSession(String sessionId) {
        Optional<Session> session = sessionRepository.findById(sessionId);

        if (session.isEmpty()) return Optional.empty();

        Integer userId = session.get().getUserId();

        return userRepository.findById(userId);
    }

    public Optional<User> getUserFromSession(HttpServletRequest req) {
        String sessionId = this.getSessionId(req.getCookies());
        return getUserFromSession(sessionId);
    }
}
