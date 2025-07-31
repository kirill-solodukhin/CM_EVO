package org.example;

import org.example.Calculator.CalculatorEngine;
import org.example.Calculator.ICalculatorEngine;
import org.example.Exceptions.AlreadyExistsOperationException;
import org.example.Exceptions.NotFoundOperationException;
import org.example.Parser.IParser;
import org.example.Parser.Parser;

import java.util.Scanner;
import java.util.function.Function;

public class Main
{
    public static void main(String[] args)
    {
        ICalculatorEngine calculator = new CalculatorEngine();
        IParser parser = new Parser();

        try
        {
            // пример определяемых операций
            // (сейчас их добавление в калькулятор не реализовано - это ваша задача)
            Function<Double, Double> sqrt  = Math::sqrt;
            calculator.defineOperation("sqrt", sqrt);

            // можно использовать одинаковое имя для операций с разным количеством аргументов
            calculator.defineOperation("-", a -> -a);
            calculator.defineOperation("-", (a, b) -> a - b);

            // обратите внимание: подставляется напрямую метод класса Math
            // это эквивалентно calculator.DefineOperation("^", (x, y) -> Math.Pow(x, y)), но лаконичнее
            calculator.defineOperation("^", Math::pow);

            // ... определите остальные операции здесь ...
        }
        catch (AlreadyExistsOperationException exception)
        {
            System.out.println(exception.getMessage());
        }

        Evaluator evaluator = new Evaluator(calculator, parser);
        System.out.println("Please enter expressions: ");
        Scanner scanner = new Scanner(System.in);
        String line;

        while (true)
        {
            line = scanner.nextLine();

            if(line == null || line.trim().isEmpty())
            {
                break;
            }

            try
            {

            }
            catch (NotFoundOperationException _)
            {
                // todo сообщение об ошибке
            }

            // todo: кажется здесь мы "отловили" только одно
            // исключение NotFoundOperationException,
            // не забудьте отловить оставшиеся

        }
    }
}