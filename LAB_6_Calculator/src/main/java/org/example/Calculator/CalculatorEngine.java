package org.example.Calculator;

import org.example.Operation;

import java.util.function.BiFunction;
import java.util.function.Function;

public class CalculatorEngine implements ICalculatorEngine
{
    @Override
    public double performOperation(Operation operation)
    {
        String operationSign = operation.sign();
        // Сейчас наш калькулятор знает три операции.
        // Необходимо добавить возможность “обучения” калькулятора новым операциям.
        // Пример обучения есть в классе Program.cs
        // Очевидно, что от Switch-а придется избавиться.
        // Достойной альтернативой Switch-у может быть, например,
        // словарь в котором ключём будет строка (знак операции),
        // а значением будет делегат или лямбда-выражение.
        //
        // todo: Переработайте метод PerformOperation()
        //
        // предлагаемая реализация с помощью cловаря (словарей):
        // ищем знак операции в словарях
        // если находим, выполняем найденную лямбду с помощью параметров,
        //   передаваемых в operation
        //
        // Если что-то пойдет не так, не забудьте сгенерировать
        // соответствующее исключение из папки Exceptions
        //
        // Обратите внимание на юнит-тесты для этого класса

        return switch (operationSign)
        {
            case "+" -> operation.params()[0] + operation.params()[1];
            case "++" -> operation.params()[0] + 1;
            case "*" -> operation.params()[0] * operation.params()[1];
            default -> 0;
        };
    }

    // todo: реализуйте методы DefineOperation().
    // метод должен добавить новую операцию в калькулятор
    //
    // предлагаемая реализация с помощью cловаря (словарей):
    //  - проверка на существование операции
    //  - добавление новой операции в словарь
    // Если что-то пойдет не так, не забудьте сгенерировать
    // соответствующее исключение из папки Exceptions
    //
    // Обратите внимание на юнит-тесты для этого класса


    @Override
    public void defineOperation(String sign, ThreeParamFunc func)
    {

    }

    @Override
    public void defineOperation(String sign, BiFunction<Double, Double, Double> func)
    {

    }

    @Override
    public void defineOperation(String sign, Function<Double, Double> func)
    {

    }
}
