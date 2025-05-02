package com.barreirasapp.dto;

import com.barreirasapp.exceptions.ValidationError;
import com.barreirasapp.model.entities.valueobjects.Email;

import java.util.HashMap;
import java.util.Map;

public class LoginDTO {
    public Email email;
    public String password;
    Map<String, String> errors = new HashMap<>();

    public LoginDTO(String email, String password) throws ValidationError {

        if (email == null || email.isEmpty()) {
            this.errors.put("email" , "Este campo é obrigatório");
        }

        if (password == null || password.isEmpty()) {
            this.errors.put("password" , "Este campo é obrigatório");
        }

        validateEmail(email);
        validatePassword(password);

        if (!errors.isEmpty()) {
            throw new ValidationError("Campos inválidos", this.getErrors());
        }
    }

    public void validateEmail(String email) {
        if (!Email.isValid(email)) {
            errors.put("email", "E-mail não válido");
            return;
        }
        this.email = new Email(email);
    }

    public void validatePassword(String password) {
        this.password = password;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}
