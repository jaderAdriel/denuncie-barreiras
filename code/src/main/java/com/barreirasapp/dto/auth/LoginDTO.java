package com.barreirasapp.dto.auth;

import com.barreirasapp.exceptions.ValidationError;
import com.barreirasapp.model.entities.valueobjects.Email;
import com.barreirasapp.utils.Validator;

import java.util.HashMap;
import java.util.Map;

public class LoginDTO {
    public Email email;
    public String password;
    Map<String, String> errors = new HashMap<>();

    public LoginDTO(String email, String password) throws ValidationError {
        Map<String, String> requiredFields = new HashMap<>();
        requiredFields.put("email" , email);
        requiredFields.put("password", password);

        Validator.checkRequiredFields(requiredFields);

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
