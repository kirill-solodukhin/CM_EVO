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
        Operation operation = parser.parse(inputString);
        return String.valueOf(calculatorEngine.performOperation(operation) );
    }
}
