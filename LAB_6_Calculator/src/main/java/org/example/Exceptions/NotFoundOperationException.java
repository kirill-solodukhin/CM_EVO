package org.example.Exceptions;

public class NotFoundOperationException extends RuntimeException
{
    public NotFoundOperationException(String message) {
        super(message);
    }

  // Выбрасываем это исключение когда в методе ICalculatorEngine.PerformOperation вызывается
  // несуществующая операция.

}
