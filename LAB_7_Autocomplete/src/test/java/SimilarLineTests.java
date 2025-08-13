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

    @Test
    public void isBetter_compressionSimilarLine_ShouldReturnFalse()
    {
        SimilarLine first = new SimilarLine("abcd", 3);

        Assertions.assertFalse(first.isBetter(first));
    }

    @Test
    public void isBetter_littleScoreCompressionBigScore_shouldReturnFalse()
    {
        SimilarLine first = new SimilarLine("abcd", 3);
        SimilarLine second = new SimilarLine("zxacvd", 2);

        Assertions.assertFalse(second.isBetter(first));
    }

    @Test
    public void isBetter_littleLengthCompressionBigLength_shouldReturnFalse()
    {
        SimilarLine first = new SimilarLine("abcd", 3);
        SimilarLine second = new SimilarLine("abcdzxcv", 3);

        Assertions.assertFalse(second.isBetter(first));
    }
}
