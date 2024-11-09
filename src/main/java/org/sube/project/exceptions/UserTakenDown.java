package org.sube.project.exceptions;

import org.sube.project.accounts.User;

public class UserTakenDown extends Exception{
    public UserTakenDown (String message){
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
