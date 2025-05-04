package com.barreirasapp.dto.law;

import com.barreirasapp.exceptions.ValidationError;
import com.barreirasapp.model.entities.User;
import com.barreirasapp.utils.Validator;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class RegisterLawDTO {
    private String code;
    private String title;
    private String description;
    private String officialLink;
    private LocalDate date;

    Map<String, String> errors = new HashMap<>();
    
    public RegisterLawDTO(String code, String title, String description, String officialLink, String date) throws ValidationError {
        Map<String, String> requiredFields = new HashMap<>();
        requiredFields.put("code" , code);
        requiredFields.put("title", title);
        requiredFields.put("date", date);
        requiredFields.put("officialLink", officialLink);
        requiredFields.put("description", description);

        Validator.checkRequiredFields(requiredFields);

        this.setCode(code);
        this.setDescription(description);
        this.setTitle(title);
        this.setDate(date);
        this.setOfficialLink(officialLink);

        if (!errors.isEmpty()) {
            throw new ValidationError("Campos inválidos", this.getErrors());
        }
    }

    private void setCode(String code) {
        this.code = code;
    }

    private void setTitle(String title) {
        this.title = title;
    }

    private void setDescription(String description) {
        this.description = description;
    }

    private void setOfficialLink(String officialLink) {
        this.officialLink = officialLink;
    }

    private void setDate(String date) {
        try {
            this.date =  LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            errors.put("date", "Valor inválido");
        }
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getOfficialLink() {
        return officialLink;
    }

    public LocalDate getDate() {
        return date;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

}
