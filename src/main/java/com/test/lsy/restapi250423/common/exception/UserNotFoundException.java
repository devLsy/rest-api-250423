package com.test.lsy.restapi250423.common.exception;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(Long id) {
        super("User Not Found with id: " + id);
    }
}
