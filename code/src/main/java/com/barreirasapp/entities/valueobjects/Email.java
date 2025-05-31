package com.barreirasapp.entities.valueobjects;
import java.util.regex.Pattern;

public record Email(String value) {
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$"
    );

    public Email {
        if (value == null) {
            throw new IllegalArgumentException("Email cannot be null");
        }

        value = value.trim();
        if (!EMAIL_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }

    public static boolean isValid(String email) {
        return email != null && EMAIL_PATTERN.matcher(email.trim()).matches();
    }

    @Override
    public boolean equals(Object o) {
        return this == o ||
                (o instanceof Email email &&
                        value.equalsIgnoreCase(email.value));
    }

    @Override
    public int hashCode() {
        return value.toLowerCase().hashCode();
    }
}
