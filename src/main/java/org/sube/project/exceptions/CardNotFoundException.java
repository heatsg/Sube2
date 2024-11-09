package org.sube.project.exceptions;

public class CardNotFoundException extends Exception {
    public CardNotFoundException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return super.getMessage();
    }
}
