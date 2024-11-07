package org.sube.project.exceptions;

public class UserNotFoundException extends Exception {
    public UserNotFoundException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return super.getMessage();
    }
}
