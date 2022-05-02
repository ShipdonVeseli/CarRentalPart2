package com.example.carrentaluser.exception;

import java.text.MessageFormat;

public class UsernameAlreadyExistsException extends RuntimeException {
    public UsernameAlreadyExistsException(String username) {
        super(MessageFormat.format("Username {0} already exists.", username));
    }
}
