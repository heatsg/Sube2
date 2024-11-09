package org.sube.project.exceptions;

public class InsuficientCharactersException extends Exception{
    public InsuficientCharactersException(String message){
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
