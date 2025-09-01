package org.example.exceptions;

public class UserNotFindException extends RuntimeException {
    public UserNotFindException(String message) {
        super(message);
    }
}
