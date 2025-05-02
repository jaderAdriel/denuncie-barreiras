package com.barreirasapp.exceptions;

import java.util.HashMap;
import java.util.Map;

public class ValidationError extends Exception {
    Map<String, String> errors = new HashMap<>();
    public ValidationError(String message, Map<String, String> errors) {
        super(message);
        this.errors = errors;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}
