package org.sube.project.exceptions;

public class UserTakenDownException extends Exception{
        public UserTakenDownException(String message){
            super(message);
        }

        @Override
        public String getMessage() {
            return super.getMessage();
        }
    }

