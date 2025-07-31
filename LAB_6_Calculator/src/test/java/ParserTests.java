import org.example.Exceptions.IncorrectParametersException;
import org.example.Operation;
import org.example.Parser.Parser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ParserTests
{
    @Test
    public void Parse_UnaryOperation_ShouldParse()
    {
        Parser parser = new Parser();
        Operation expected = new Operation("++", new double[] {3});
        var actual = parser.Parse(" ++ 3");

        Assertions.assertEquals(expected.sign(), actual.sign(), "Sign incorrectly defined");
        Assertions.assertEquals(1, actual.params().length, "Parameters count mismatch");
        Assertions.assertEquals(expected.params()[0], actual.params()[0], "First option incorrectly defined");
    }

    @Test
    public void Parse_BinaryOperation_ShouldParse()
    {
        var expected = new Operation("-", new double[] { 5.0, 3.0 });

        var parser = new Parser();
        var actual = parser.Parse("- 5   3 ");

        Assertions.assertEquals(expected.sign(), actual.sign(), "Sign incorrectly defined");
        Assertions.assertEquals(2, actual.params().length, "Parameters count mismatch");
        Assertions.assertEquals(expected.params()[0], actual.params()[0], "First option incorrectly defined");
        Assertions.assertEquals(expected.params()[1], actual.params()[1], "Second option incorrectly defined");
    }

    @Test
    public void Parse_TernaryOperation_ShouldParse()
    {
        var expected = new Operation("T5", new double[] { 5.0, 3.0, 4.0 });

        var parser = new Parser();
        var actual = parser.Parse(" T5 5 3   4 ");

        Assertions.assertEquals(expected.sign(), actual.sign(), "Sign incorrectly defined");
        Assertions.assertEquals(3, actual.params().length, "Parameters count mismatch");
        Assertions.assertEquals(expected.params()[0], actual.params()[0], "First option incorrectly defined");
        Assertions.assertEquals(expected.params()[1], actual.params()[1], "Second option incorrectly defined");
        Assertions.assertEquals(expected.params()[2], actual.params()[2], "Third option incorrectly defined");
    }

    @Test
    public void Parse_WrongInput_ShouldThrow()
    {
        var parser = new Parser();

        Assertions.assertThrows(IncorrectParametersException.class,() -> parser.Parse(" +"));
    }
}
