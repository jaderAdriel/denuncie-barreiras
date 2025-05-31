package com.barreirasapp.entities.enums;

public enum UserRole {
    ADMIN("Adiministrador"),
    MODERATOR("Moderador"),
    COMMON("Usuário");

    private final String translation;

    UserRole(String translation) {
        this.translation = translation;
    }

    public String getTranslation() {
        return translation;
    }
}
