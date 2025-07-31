package org.example.Exceptions;

public class FigureNameAlreadyExistsException extends RuntimeException {
    public FigureNameAlreadyExistsException(String message) {
        super(message);
    }
}
