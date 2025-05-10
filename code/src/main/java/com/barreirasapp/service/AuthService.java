package com.barreirasapp.service;

import com.barreirasapp.dto.auth.LoginDTO;
import com.barreirasapp.dto.auth.RegisterUserDTO;
import com.barreirasapp.exceptions.ValidationError;
import com.barreirasapp.infra.security.UserContext;
import com.barreirasapp.model.Session;
import com.barreirasapp.model.dao.DaoFactory;
import com.barreirasapp.model.dao.UserDao;
import com.barreirasapp.model.entities.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.*;

public class AuthService {

    public void register(RegisterUserDTO registerUserDTO) throws ValidationError {

        Optional<User> existentUser = User.find(registerUserDTO.getEmail());

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
        user.save();
    }

    public void logout(HttpServletRequest req, HttpServletResponse resp) {
        String sessionId = UserContext.getSessionId(req.getCookies());

        UserContext.removeSession(sessionId);

        resp.setHeader("Set-Cookie", createCookie(null));
    }

    public void login(LoginDTO loginDTO, HttpServletRequest req, HttpServletResponse resp) throws ValidationError {
        Optional<User> user = User.find(loginDTO.email);

        if (user.isEmpty()) {
            throw new ValidationError( "" ,Map.of("email", "Usuário não encontrado"));
        }

        User foundedUser = user.get();

        if (!foundedUser.checkPassword(loginDTO.password)) {
            throw new ValidationError( "" ,Map.of("password", "Senha incorreta"));
        }

        Session session = UserContext.createSession(foundedUser);

        resp.setHeader("Set-Cookie", createCookie(session.getSessionId()));
    }

    public String createCookie(String sessionId) {
        return "sessionId=" + sessionId + "; Path=/; HttpOnly";
    }
}
