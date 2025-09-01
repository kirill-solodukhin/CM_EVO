package org.example.exceptions;

public class UsersCountException extends RuntimeException {
  public UsersCountException(String message) {
    super(message);
  }
}
