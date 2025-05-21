package com.barreirasapp.model.enums;

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

    private final String translation;

    BarrierType(String translation) {
        this.translation = translation;
    }

    public String getTranslation() {
        return translation;
    }
}
