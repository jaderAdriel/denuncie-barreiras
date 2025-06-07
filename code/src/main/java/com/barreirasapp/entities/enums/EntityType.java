package com.barreirasapp.entities.enums;

public enum EntityType {
    SCHOOL("Escola"),
    COMPANY("Empresa");

    private final String translation;

    EntityType(String translation) {
        this.translation = translation;
    }
    public String getTranslation() {
        return translation;
    }
}
