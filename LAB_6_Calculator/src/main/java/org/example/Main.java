package org.example;

import org.example.Calculator.CalculatorEngine;
import org.example.Calculator.ICalculatorEngine;
import org.example.Calculator.OneParamFunc;
import org.example.Exceptions.AlreadyExistsOperationException;
import org.example.Exceptions.IncorrectParametersException;
import org.example.Exceptions.NotFoundOperationException;
import org.example.Exceptions.ParametersCountMismatchException;
import org.example.Parser.IParser;
import org.example.Parser.Parser;

import java.util.Scanner;


public class Main
{
    public static void main(String[] args)
    {
        ICalculatorEngine calculator = new CalculatorEngine();
        IParser parser = new Parser();

        try
        {
            OneParamFunc sqrt = Math::sqrt;
            calculator.defineOperation("sqrt", sqrt);


            calculator.defineOperation("-", a -> -a);
            calculator.defineOperation("-", (a, b) -> a - b);
            calculator.defineOperation("-", (a, b, c) -> a - b - c);

            calculator.defineOperation("+", a -> a);
            calculator.defineOperation("+", Double::sum);
            calculator.defineOperation("+", (a, b, c) -> a + b + c);

            calculator.defineOperation("*", (a, b) -> a * b);
            calculator.defineOperation("*", (a, b, c) -> a * b * c);

            calculator.defineOperation("/", (a, b) -> a / b);
            calculator.defineOperation("/", (a, b, c) -> a / b / c);

            calculator.defineOperation("^", Math::pow);
            calculator.defineOperation("abs", Math::abs);



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
                System.out.println(evaluator.calculate(line));
            }
            catch (NotFoundOperationException | IncorrectParametersException | ParametersCountMismatchException exception)
            {
                System.out.println(exception.getMessage());
            }
        }
    }
}