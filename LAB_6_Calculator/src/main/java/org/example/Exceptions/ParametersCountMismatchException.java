package org.example.Exceptions;

public class ParametersCountMismatchException extends RuntimeException
{
    public ParametersCountMismatchException(String message) {
        super(message);
    }

  // Выбрасываем это исключение когда в методе ICalculatorEngine.PerformOperation вызывается
  // операция с неверным числом параметров.

}
