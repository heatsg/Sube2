package org.sube.project.card;

public enum CardType {
    NORMAL_CARD("Normal"),
    STUDENT("Estudiante"),
    TEACHER("Docente"),
    DISABLED_PERSON("Discapacitado"),
    RETIRED("Jubilado");

    private final String description;

    CardType(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
