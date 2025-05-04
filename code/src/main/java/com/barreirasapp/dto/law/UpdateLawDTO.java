package com.barreirasapp.dto.law;

import com.barreirasapp.exceptions.ValidationError;
import com.barreirasapp.utils.Validator;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

public class UpdateLawDTO {
    private String code;
    private String description;
    private String officialLink;

    Map<String, String> errors = new HashMap<>();

    public UpdateLawDTO(String code, String description, String officialLink) throws ValidationError {
        Map<String, String> requiredFields = new HashMap<>();
        requiredFields.put("code" , code);

        Validator.checkRequiredFields(requiredFields);

        this.setCode(code);
        this.setDescription(description);
        this.setOfficialLink(officialLink);
    }

    private void setCode(String code) {
        this.code = code;
    }

    private void setDescription(String description) {
        this.description = description;
    }

    private void setOfficialLink(String officialLink) {
        this.officialLink = officialLink;
    }

    public String getCode() {
        return this.code;
    }

    public String getDescription() {
        return description;
    }

    public String getOfficialLink() {
        return officialLink;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

}
