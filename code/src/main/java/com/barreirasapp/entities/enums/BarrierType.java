package com.barreirasapp.entities.enums;

public enum BarrierType {
    EXCLUSION("Exclusão"),
    PREJUDICE("Preconceito"),
    DISCRIMINATION("Discriminação"),
    BULLYING("Bullying"),
    VERBAL_VIOLENCE("Violência verbal"),
    PHYSICAL_VIOLENCE("Violência física"),
    PEJORATIVE_LANGUAGE("Linguagem pejorativa"),
    ABLEISM("Capacitismo"),
    UNEQUAL_TREATMENT("Tratamento desigual"),
    OTHER("Outro");

    private String translation;

    BarrierType(String translation) {
        this.translation = translation;
    }

    public String getTranslation() {
        return translation;
    }

    public static BarrierType fromValue(String typeValue) {
        try {
            return BarrierType.valueOf(typeValue);
        } catch (IllegalArgumentException e) {
            BarrierType other = BarrierType.OTHER;
            other.translation = typeValue; // muda a tradução dinamicamente
            return other;
        }
    }
}
