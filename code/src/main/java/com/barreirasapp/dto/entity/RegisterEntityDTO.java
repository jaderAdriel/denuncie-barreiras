package com.barreirasapp.dto.entity;

import com.barreirasapp.entities.Address;
import com.barreirasapp.entities.enums.EntityType;
import com.barreirasapp.exceptions.ValidationError;
import com.barreirasapp.utils.Validator;
import java.util.HashMap;
import java.util.Map;

public class RegisterEntityDTO {
    private String cnpj;
    private String name;
    private String phone;
    private EntityType type;

    Map<String, String> errors = new HashMap<>();

    public RegisterEntityDTO(String cnpj, String name, String phone, String type) throws ValidationError {
        Map<String, String> requiredFields = new HashMap<>();
        requiredFields.put("cnpj", cnpj);
        requiredFields.put("name", name);

        Validator.checkRequiredFields(requiredFields);

        this.setCnpj(cnpj);
        this.setName(name);
        this.setPhone(phone);
        this.setType(type);

        if (!errors.isEmpty()) {
            throw new ValidationError("Campos inválidos", this.getErrors());
        }
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EntityType getType() {
        return type;
    }

    public void setType(String type) {
        try {
            this.type = EntityType.valueOf(type);
        } catch (NumberFormatException e) {
            errors.put("type", "Valor inválido");
        }
    }

    public Map<String, String> getErrors() {
        return errors;
    }

}

