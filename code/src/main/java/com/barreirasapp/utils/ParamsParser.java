package com.barreirasapp.utils;

import java.util.HashMap;

public class ParamsParser {
    private HashMap<String, String> violations;

    public static Integer parseInteger(String value) throws IllegalArgumentException {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Valor inválido para Integer: " + value, e);
        }
    }

    public static Double parseDouble(String value) throws IllegalArgumentException {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Valor inválido para Double: " + value, e);
        }
    }
}
