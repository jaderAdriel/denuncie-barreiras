package com.barreirasapp.model.enums;

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

    private final String translation;

    EnvironmentType(String translation) {
        this.translation = translation;
    }

    public String getTranslation() {
        return translation;
    }

}