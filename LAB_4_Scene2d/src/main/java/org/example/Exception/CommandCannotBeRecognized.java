package org.example.Exception;

public class CommandCannotBeRecognized extends RuntimeException {
    public CommandCannotBeRecognized(String message) {
        super(message);
    }
}
