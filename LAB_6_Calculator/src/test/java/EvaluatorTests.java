import ParserStub.ParserStub;
import org.example.Calculator.ICalculatorEngine;
import org.example.Evaluator;
import org.example.Operation;
import org.example.Parser.IParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ParserStub.CalculatorEngineStub;

import java.util.Map;

public class EvaluatorTests
{
    @Test
    public void Calculate_CorrectOperation_ShouldCallParserAndCalculatorEngine()
    {
        Operation operation = new Operation("test", new double[] { 1d, 2d });
        IParser parser = new ParserStub(operation);
        ICalculatorEngine calculator = new CalculatorEngineStub(Map.of(operation, 42d));

        var evaluator = new Evaluator(calculator, parser);

        Assertions.assertEquals("42.0", evaluator.calculate("any"));
    }

}
