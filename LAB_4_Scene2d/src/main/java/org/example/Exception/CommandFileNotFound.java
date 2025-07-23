package org.example.Exception;

public class CommandFileNotFound extends RuntimeException {
    public CommandFileNotFound(String message) {
        super(message);
    }
}
