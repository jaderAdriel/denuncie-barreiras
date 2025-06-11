package com.barreirasapp.entities.enums;

public enum EntityType {
    SCHOOL("Escola"),
    COMPANY("Empresa"),
    OTHER("Outro");

    String translation;

    EntityType(String translation) {
        this.translation = translation;
    }
    public String getTranslation() {
        return translation;
    }

    public static EntityType fromValue(String typeValue) {
        try {
            return EntityType.valueOf(typeValue);
        } catch (IllegalArgumentException e) {
            EntityType other = EntityType.OTHER;
            other.translation = typeValue; // muda a tradução dinamicamente
            return other;
        }
    }
}
