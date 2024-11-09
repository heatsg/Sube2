package org.sube.project.exceptions;

public class EmptyTokenerException extends Exception {
    public EmptyTokenerException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return super.getMessage();
    }
}
