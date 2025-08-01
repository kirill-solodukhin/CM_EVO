package ParserStub;

import org.example.Calculator.ICalculatorEngine;
import org.example.Calculator.OneParamFunc;
import org.example.Calculator.ThreeParamFunc;
import org.example.Calculator.TwoParamFunc;
import org.example.Operation;

import java.util.Map;

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
    public void defineOperation(String sign, TwoParamFunc func)
    {
        throw new RuntimeException("Calculator Engine Stub does not support operations definitions");
    }

    @Override
    public void defineOperation(String sign, OneParamFunc func)
    {
        throw new RuntimeException("Calculator Engine Stub does not support operations definitions");
    }
}
