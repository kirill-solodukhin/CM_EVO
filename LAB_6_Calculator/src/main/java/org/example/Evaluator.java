package org.example;

import org.example.Calculator.ICalculatorEngine;
import org.example.Parser.IParser;

public class Evaluator
{
    private final ICalculatorEngine calculatorEngine;
    private final IParser parser;

    public Evaluator(ICalculatorEngine calculatorEngine, IParser parser)
    {
        this.calculatorEngine = calculatorEngine;
        this.parser = parser;
    }

    public String calculate(String inputString)
    {
        // todo: реализуйте метод Calculate().
        // Здесь вам нужно получить значение для выражения из inputString,
        // используя экземпляры классов Calculator и Parser
        // соответственно для распарсивания строки и вычисления выражения
        //
        // Обратите внимание на юнит-тесты для этого класса

        return null;
    }
}
