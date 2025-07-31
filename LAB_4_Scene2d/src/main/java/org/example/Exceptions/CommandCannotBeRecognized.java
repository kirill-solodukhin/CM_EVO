package org.example.Exceptions;

public class CommandCannotBeRecognized extends RuntimeException {
    public CommandCannotBeRecognized(String message) {
        super(message);
    }
}
