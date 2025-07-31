package org.example.Exceptions;

public class CommandFileNotFound extends RuntimeException {
    public CommandFileNotFound(String message) {
        super(message);
    }
}
