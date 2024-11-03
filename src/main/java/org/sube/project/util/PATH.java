package org.sube.project.util;

public enum PATH {
    ID_COUNTERS("counters"),
    CARD("cards"),
    USER("users");

    private final String path;
    PATH(String path) { // en registry se guardarían los .json para que esté más ordenado no sé
        this.path = "registry/" + path + ".json";
    }

    @Override
    public String toString() {
        return path;
    }
}
