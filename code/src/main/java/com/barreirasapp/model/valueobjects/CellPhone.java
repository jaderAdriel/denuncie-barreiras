package com.barreirasapp.model.valueobjects;

import java.util.regex.Pattern;

public record CellPhone(String value) {
    private static final Pattern PHONE_PATTERN = Pattern.compile(
            "^(\\+\\d{1,3}[- ]?)?\\(?(\\d{2,3})\\)?[- ]?(\\d{4,5})[- ]?(\\d{4})$"
    );

    public CellPhone {
        if (value == null) {
            throw new IllegalArgumentException("Cell phone cannot be null");
        }

        String cleanedValue = cleanPhoneNumber(value);
        if (!isValid(cleanedValue)) {
            throw new IllegalArgumentException("Invalid cell phone format");
        }
        value = formatPhoneNumber(cleanedValue);
    }

    public static boolean isValid(String phoneNumber) {
        if (phoneNumber == null) return false;
        String cleaned = cleanPhoneNumber(phoneNumber);
        return PHONE_PATTERN.matcher(cleaned).matches();
    }

    private static String cleanPhoneNumber(String phoneNumber) {
        return phoneNumber.replaceAll("[^0-9+]", "");
    }

    private static String formatPhoneNumber(String phoneNumber) {
        // Formats as (XX) XXXXX-XXXX for Brazilian numbers
        if (phoneNumber.matches("^\\d{11}$")) { // With DDD
            return String.format("(%s) %s-%s",
                    phoneNumber.substring(0, 2),
                    phoneNumber.substring(2, 7),
                    phoneNumber.substring(7));
        }
        return phoneNumber; // Return as-is if doesn't match expected format
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CellPhone cellPhone = (CellPhone) o;
        return cleanPhoneNumber(value).equals(cleanPhoneNumber(cellPhone.value));
    }

    @Override
    public int hashCode() {
        return cleanPhoneNumber(value).hashCode();
    }

    @Override
    public String toString() {
        return value;
    }
}