package org.sube.project.card;

public enum CardType {
    NORMAL_CARD(1, "Normal"),
    STUDENT(0.8, "Estudiante"),
    TEACHER(0.8, "Docente"),
    DISABLED_PERSON(0.5, "Discapacitado"),
    RETIRED(0.45, "Jubilado");

    private final double percentage;
    private final String description;

    CardType(double percentage, String description) {
        this.percentage = percentage;
        this.description = description;
    }

    public double getPercentage() {
        return percentage;
    }

    public double getFinalPrice(double amount) {
        return amount * percentage;
    }

    @Override
    public String toString() {
        return description;
    }
}
