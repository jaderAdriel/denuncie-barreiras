package com.barreirasapp.utils;

import com.barreirasapp.exceptions.ValidationError;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Validator {
    public static void checkRequiredFields(Map<String, String> requiredFields) throws ValidationError {
        Map<String, String> errors = new HashMap<>();

        for (String field : requiredFields.keySet()) {
            String value = requiredFields.get(field);
            if (value == null || value.isEmpty()) {
                errors.put(field, "Este campo é obrigatório");
            }
        }

        if (!errors.isEmpty()) {
            throw new ValidationError("Campos requeridos", errors);
        }
    }
}
