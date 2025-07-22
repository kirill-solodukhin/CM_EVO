package org.example.Exception;

public class BadArgumentException extends RuntimeException {
  public BadArgumentException(String message) {
    super(message);
  }
}
