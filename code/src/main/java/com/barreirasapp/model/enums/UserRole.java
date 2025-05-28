package com.barreirasapp.model.enums;

public enum UserRole {
    ADMIN("Adiministrador"),
    MODERATOR("Moderador"),
    standard("Comum");

    private final String translation;

    UserRole(String translation) {
        this.translation = translation;
    }

    public String getTranslation() {
        return translation;
    }
}
