package org.sube.project.exceptions;

public class PasswordNotEqualsException extends Exception{
    public PasswordNotEqualsException (String message){
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
