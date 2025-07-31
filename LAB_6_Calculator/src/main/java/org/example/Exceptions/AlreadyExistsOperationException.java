package org.example.Exceptions;

public class AlreadyExistsOperationException extends RuntimeException
{
    public AlreadyExistsOperationException(String message) {
        super(message);
    }

  // Выбрасываем это исключение когда в методе ICalculatorEngine.DefineOperations регистрируется
  // существующая операция с тем же количеством параметров.

}
