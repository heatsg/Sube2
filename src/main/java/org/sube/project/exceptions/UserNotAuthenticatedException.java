package org.sube.project.exceptions;

public class UserNotAuthenticatedException extends Exception {
    public UserNotAuthenticatedException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return super.getMessage();
    }
}
