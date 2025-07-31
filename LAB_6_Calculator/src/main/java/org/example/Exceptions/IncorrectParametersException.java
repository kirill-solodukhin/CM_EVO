package org.example.Exceptions;

public class IncorrectParametersException extends RuntimeException
{
  public IncorrectParametersException(String message) {
    super(message);
  }

  // Выбрасываем это исключение когда метод IParser.Parse не находит
  // ни одного аргумента во входной строке.

}
