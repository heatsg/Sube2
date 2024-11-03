package org.sube.project.util;

public enum ID_TYPE {
    USER("user"),
    TRANSACTION("transaction"),
    CARD("card");

    private final String type;

    ID_TYPE(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
