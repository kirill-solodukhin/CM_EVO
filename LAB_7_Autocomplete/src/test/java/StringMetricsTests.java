import org.example.Strings.StringMetrics;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StringMetricsTests
{
    @Test
    public void longestCommonSubstringLength_nullString_shouldNotThrowException()
    {
        Assertions.assertDoesNotThrow(
                () -> StringMetrics.longestCommonSubstringLength(null, null));
    }

    @Test
    public void longestCommonSubstringLength_stringFirstIsEmpty_ShouldReturnZero()
    {
        Assertions.assertEquals(
                0,
                StringMetrics.longestCommonSubstringLength("", "string"));
    }

    @Test
    public void longestCommonSubstringLength_stringSecondIsEmpty_ShouldReturnZero()
    {
        Assertions.assertEquals(
                0,
                StringMetrics.longestCommonSubstringLength("string", ""));
    }

    @Test
    public void longestCommonSubstringLength_sameStrings_ShouldReturnStringLength()
    {
        Assertions.assertEquals(
                "string".length(),
                StringMetrics.longestCommonSubstringLength("string", "string"));
    }

    @Test
    public void longestCommonSubstringLength_sameStringsInDifferentReg_ShouldReturnStringLength()
    {
        Assertions.assertEquals(
                "string".length(),
                StringMetrics.longestCommonSubstringLength("STRING", "string"));
    }

    @Test
    public void longestCommonSubstringLength_max3_ShouldReturn3()
    {
        Assertions.assertEquals(
                3,
                StringMetrics.longestCommonSubstringLength("ABCDEF", "ACF"));
    }

    @Test
    public void longestCommonSubstringLength_firstStringLessSecond_ShouldReturn3()
    {
        Assertions.assertEquals(
                3,
                StringMetrics.longestCommonSubstringLength("ACF", "ABCDEF"));
    }
}
