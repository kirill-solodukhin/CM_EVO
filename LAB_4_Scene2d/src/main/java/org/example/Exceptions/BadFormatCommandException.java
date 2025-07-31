package org.example.Exceptions;

public class BadFormatCommandException extends RuntimeException {
  public BadFormatCommandException(String message) {
    super(message);
  }
}
