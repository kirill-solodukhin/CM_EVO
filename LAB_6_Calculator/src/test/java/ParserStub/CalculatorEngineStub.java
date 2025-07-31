package ParserStub;

import org.example.Calculator.ICalculatorEngine;
import org.example.Calculator.ThreeParamFunc;
import org.example.Operation;

import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

public class CalculatorEngineStub implements ICalculatorEngine
{
    private final Map<Operation, Double> results;

    public CalculatorEngineStub(Map<Operation, Double> results)
    {
        this.results = results;
    }

    @Override
    public double performOperation(Operation operation)
    {
        if(results.containsKey(operation))
        {
            return results.get(operation);
        }

        throw new RuntimeException("Operation not found: " + operation.sign());
    }

    @Override
    public void defineOperation(String sign, ThreeParamFunc func)
    {
        throw new RuntimeException("Calculator Engine Stub does not support operations definitions");
    }

    @Override
    public void defineOperation(String sign, BiFunction<Double, Double, Double> func)
    {
        throw new RuntimeException("Calculator Engine Stub does not support operations definitions");
    }

    @Override
    public void defineOperation(String sign, Function<Double, Double> func)
    {
        throw new RuntimeException("Calculator Engine Stub does not support operations definitions");
    }
}
