package org.example.Exceptions;

public class FigureOrSceneIsNotExistsException extends RuntimeException {
    public FigureOrSceneIsNotExistsException(String message) {
        super(message);
    }
}
