package org.sube.project.util;

public enum Path {
    ID_COUNTERS("counters"),
    CARD("cards"),
    USER("accounts"),
    TRANSACTION("transactions"),
    UNCREDITED("uncreditedAmounts");

    private final String path;
    Path(String path) { // en registry se guardarían los .json para que esté más ordenado no sé
        this.path = "registry/" + path + ".json";
    }

    @Override
    public String toString() {
        return path;
    }
}
