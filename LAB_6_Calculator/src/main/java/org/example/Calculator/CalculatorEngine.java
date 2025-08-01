package org.example.Calculator;

import org.example.Exceptions.AlreadyExistsOperationException;
import org.example.Exceptions.NotFoundOperationException;
import org.example.Exceptions.ParametersCountMismatchException;
import org.example.Operation;

import java.util.HashMap;
import java.util.Map;

public class CalculatorEngine implements ICalculatorEngine
{
    private final Map<String, Object[]> operations = new HashMap<>();

    @Override
    public double performOperation(Operation operation)
    {
        String operationSign = operation.sign();

        if(!operations.containsKey(operationSign))
        {
            throw new NotFoundOperationException("Operation not found");
        }

        return switch (operation.params().length)
        {
            case 1 ->
            {
                OneParamFunc func = (OneParamFunc) (operations.get(operationSign)[0]);
                throwIfNull(func);
                yield func.func(operation.params()[0]);
            }

            case 2 ->
            {
                TwoParamFunc func = (TwoParamFunc) operations.get(operationSign)[1];
                throwIfNull(func);
                yield func.func(operation.params()[0], operation.params()[1]);
            }

            case 3 ->
            {
                ThreeParamFunc func = (ThreeParamFunc) operations.get(operationSign)[2];
                throwIfNull(func);
                yield func.func(operation.params()[0], operation.params()[1], operation.params()[2]);
            }

            default -> throw new ParametersCountMismatchException("Exception in count parameters");
        };
    }

    @Override
    public void defineOperation(String sign, ThreeParamFunc func) // with 3 param
    {
        if(operations.containsKey(sign) && operations.get(sign)[2] != null) // operation already exists
        {
            throw new AlreadyExistsOperationException("operation: { " + sign + " } already exist");
        }

        if(operations.containsKey(sign) && operations.get(sign)[2] == null) // need add operation
        {
            Object[] functions = operations.get(sign);
            functions[2] = func;

            operations.put(sign, functions);
            return;
        }

        // need create operation
        Object[] functions = new Object[3];
        functions[2] = func;

        operations.put(sign, functions);
    }

    @Override
    public void defineOperation(String sign, TwoParamFunc func) // with 2 param
    {
        if(operations.containsKey(sign) && operations.get(sign)[1] != null) // operation already exists
        {
            throw new AlreadyExistsOperationException("operation: { " + sign + " } already exist");
        }

        if(operations.containsKey(sign) && operations.get(sign)[1] == null) // need add operation
        {
            Object[] functions = operations.get(sign);
            functions[1] = func;

            operations.put(sign, functions);
            return;
        }

        // need create operation
        Object[] functions = new Object[3];
        functions[1] = func;

        operations.put(sign, functions);
    }

    @Override
    public void defineOperation(String sign, OneParamFunc func) // with 1 param
    {
        if(operations.containsKey(sign) && operations.get(sign)[0] != null) // operation already exists
        {
            throw new AlreadyExistsOperationException("operation: { " + sign + " } already exist");
        }

        if(operations.containsKey(sign) && operations.get(sign)[0] == null) // need add operation
        {
            Object[] functions = operations.get(sign);
            functions[0] = func;

            operations.put(sign, functions);
            return;
        }

        // need create operation
        Object[] functions = new Object[3];
        functions[0] = func;

        operations.put(sign, functions);
    }

    private void throwIfNull(Object obj)
    {
        if(obj == null)
        {
            throw new ParametersCountMismatchException(" ");
        }
    }
}
