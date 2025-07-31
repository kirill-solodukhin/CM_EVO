package ParserStub;

import org.example.Calculator.CalculatorEngine;
import org.example.Exceptions.AlreadyExistsOperationException;
import org.example.Exceptions.IncorrectParametersException;
import org.example.Exceptions.NotFoundOperationException;
import org.example.Exceptions.ParametersCountMismatchException;
import org.example.Operation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

public class CalculatorTests
{
    @Test
    public void performOperation_Unary_ShouldCalculate()
    {
        var calculator = new CalculatorEngine();
        calculator.defineOperation("++", x -> x + 1);

        var operation = new Operation("++", new double[] { 3.0 });

        var actual = calculator.performOperation(operation);

        Assertions.assertEquals(4.0, actual, "Incorrect calculation result");
    }
    
    @Test
    public void performOperation_Binary_ShouldCalculate()
    {
        var calculator = new CalculatorEngine();
        calculator.defineOperation("*", (x, y) -> x * y);

        var operation = new Operation("*", new double[] { 5.0, 3.0 });
        var actual = calculator.performOperation(operation);

        Assertions.assertEquals(15d, actual, "Incorrect calculation result");
    }

    @Test
    public void performOperation_Ternary_ShouldCalculate()
    {
        var calculator = new CalculatorEngine();
        calculator.defineOperation("T5", (x, y, z) -> Math.min(x, Math.min(y, z)));

        var operation = new Operation("T5", new double[] { 2.0, 3.0, 6.0 });
        var actual = calculator.performOperation(operation);

        Assertions.assertEquals(2d, actual, "Incorrect calculation result");
    }

    @Test
    public void defineOperation_UnaryAlreadyExists_ShouldThrow()
    {
        var calculator = new CalculatorEngine();

        calculator.defineOperation("++", x -> x + 1);

        Assertions.assertThrows(AlreadyExistsOperationException.class,
                () -> calculator.defineOperation("++", x -> x +1));
    }

    @Test
    public void defineOperation_BinaryAlreadyExists_ShouldThrow()
    {
        var calculator = new CalculatorEngine();

        calculator.defineOperation("+", Double::sum);

        Assertions.assertThrows(AlreadyExistsOperationException.class,
                () ->  calculator.defineOperation("+", Double::sum));
    }

    @Test
    public void defineOperation_TernaryAlreadyExists_ShouldThrow()
    {
        var calculator = new CalculatorEngine();

        calculator.defineOperation("whatever", (x, y, z) -> x + y + z);

        Assertions.assertThrows(AlreadyExistsOperationException.class,
                () -> calculator.defineOperation("whatever", (x, y, z) -> x + y + z));
    }

    @Test
    public void defineOperation_MultipleForSameSign_ShouldNotThrow()
    {
        var calculator = new CalculatorEngine();

        calculator.defineOperation("whatever", (x) -> x);
        calculator.defineOperation("whatever", Double::sum);
        calculator.defineOperation("whatever", (x, y, z) -> x + y + z);
    }

    @Test
    public void Calculate_OperationNotFound_ShouldThrow()
    {
        var calculator = new CalculatorEngine();

        Assertions.assertThrows(NotFoundOperationException.class,
                () -> calculator.performOperation(new Operation("&&", new double[0])));
    }


    @Test
    public void Calculate_UnaryParametersMismatch_ShouldThrow()
    {
        var calculator = new CalculatorEngine();

        calculator.defineOperation("++", x -> x + 1);

        Assertions.assertThrows(ParametersCountMismatchException.class,
                () -> calculator.performOperation(new Operation("++", new double[] { 1, 2 } )));

        Assertions.assertThrows(ParametersCountMismatchException.class,
                () -> calculator.performOperation(new Operation("++", new double[] { 1, 2, 3 } )));
    }


    @Test
    public void Calculate_BinaryParametersMismatch_ShouldThrow()
    {
        var calculator = new CalculatorEngine();

        calculator.defineOperation("+", Double::sum);

        Assertions.assertThrows(ParametersCountMismatchException.class,
                () -> calculator.performOperation(new Operation("+", new double[] { 1 } )));

        Assertions.assertThrows(ParametersCountMismatchException.class,
                () -> calculator.performOperation(new Operation("+", new double[] { 1, 2, 3 } )));
    }

    @Test
    public void Calculate_TernaryParametersMismatch_ShouldThrow()
    {
        var calculator = new CalculatorEngine();

        calculator.defineOperation("+", (x, y, z) -> x + y + z);

        Assertions.assertThrows(ParametersCountMismatchException.class,
                () -> calculator.performOperation(new Operation("+", new double[] { 1 } )));

        Assertions.assertThrows(ParametersCountMismatchException.class,
                () -> calculator.performOperation(new Operation("+", new double[] { 1, 2 } )));
    }
}
