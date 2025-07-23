package org.example.Exception;

public class FigureNameAlreadyExistsException extends RuntimeException {
    public FigureNameAlreadyExistsException(String message) {
        super(message);
    }
}
