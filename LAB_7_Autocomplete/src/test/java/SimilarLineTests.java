import org.example.Strings.SimilarLine;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SimilarLineTests
{
    @Test
    public void isBetter_comparisonLittleBetterBig_ShouldReturnTrue()
    {
        SimilarLine little = new SimilarLine("abcd", 3);
        SimilarLine big = new SimilarLine("abcdefghijklmn" ,3);

        Assertions.assertTrue(little.isBetter(big));
    }
}
