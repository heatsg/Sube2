package org.sube.project.accounts;

public enum UserType {
    ADMIN("Administrador"),
    NORMAL_USER("Usuario");

    private final String description;

    UserType(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
