package com.barreirasapp.entities.enums;

public enum EnvironmentType {
    CLASSROOM("Sala de Aula"),
    LIBRARY("Biblioteca"),
    LABORATORY("Laboratorio"),
    HALLWAY("Corredores"),
    COURT("Quadra"),
    PATIO("Pátio"),
    BATHROOM("Banheiro"),
    CAFETERIA("Refeitório"),
    SCHOOL_TRANSPORT("Transporte Escolar"),
    OTHER("Outros");

    private String translation;

    EnvironmentType(String translation) {
        this.translation = translation;
    }

    public String getTranslation() {
        return translation;
    }

    public static EnvironmentType fromValue(String typeValue) {
        try {
            return EnvironmentType.valueOf(typeValue);
        } catch (IllegalArgumentException e) {
            EnvironmentType other = EnvironmentType.OTHER;
            other.translation = typeValue; // muda a tradução dinamicamente
            return other;
        }
    }

}