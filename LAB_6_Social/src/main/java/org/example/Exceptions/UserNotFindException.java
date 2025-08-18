package org.example.Exceptions;

public class UserNotFindException extends RuntimeException
{
    public UserNotFindException(String message) {
        super(message);
    }
}
