package org.sube.project.exceptions;

public class WrongPasswordException extends Exception{
    public WrongPasswordException (String message){
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
