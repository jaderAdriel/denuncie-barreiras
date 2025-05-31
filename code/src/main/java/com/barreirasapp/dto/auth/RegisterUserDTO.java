package com.barreirasapp.dto.auth;

import com.barreirasapp.exceptions.ValidationError;
import com.barreirasapp.entities.valueobjects.Email;
import com.barreirasapp.entities.enums.Gender;
import com.barreirasapp.utils.Validator;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisterUserDTO {
    private String name;
    private Email email;
    private LocalDate birthDate;
    private Gender gender;
    private String password;
    private String password2;
    Map<String, String> errors = new HashMap<>();

    List<String> requiredFields = List.of("name", "email", "birthDate", "gender", "password", "password2");

    public RegisterUserDTO(String name, String email, String password, String password2, String birthDate, String gender) throws ValidationError {
        Map<String, String> requiredFields = new HashMap<>();
        requiredFields.put("name" , name);
        requiredFields.put("email", email);
        requiredFields.put("birthDate", birthDate);
        requiredFields.put("gender", gender);
        requiredFields.put("password", password);
        requiredFields.put("password2", password2);

        Validator.checkRequiredFields(requiredFields);

        validateEmail(email);
        validateName(name);
        validatePassword(password, password2);
        validateBirthDate(birthDate);
        validateGender(gender);

        if (!errors.isEmpty()) {
            throw new ValidationError("Campos inválidos", this.getErrors());
        }
    }

    public void validateName(String name) {
        if (name == null || name.trim().split("\\s+").length < 2) {
            errors.put("name", "Informe o nome completo");
        }
        this.name = name;
    }

    public void validateEmail(String email) {
        if (!Email.isValid(email)) {
            errors.put("email", "E-mail não válido");
            return;
        }
        this.email = new Email(email);
    }


    public void validateBirthDate(String birthDate) {
        try {
            this.birthDate = LocalDate.parse(birthDate);
        } catch (DateTimeParseException e) {
            errors.put("birthDate", "Valor inválido");
        }
    }

    public void validateGender(String gender) {
        try {
            this.gender = Gender.valueOf(gender.toUpperCase());
        } catch (IllegalArgumentException e) {
            errors.put("gender", "Valor inválido");
        }
    }


    public void validatePassword(String password, String password2) {
        if (password == null || password.isEmpty()) {
            this.errors.put("password" , "Este campo é obrigatório");
            return;
        }

        if (password2.length() < 8) {
            errors.put("password", "tamanho mínimo é 8 dígitos");
            return;
        }

        if (!password.equals(password2)) {
            errors.put("password", "As senhas não batem");
            errors.put("password2", "As senhas não batem");
            return;
        }

        this.password = password;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

}
