import org.example.Strings.SearchingProcess;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SearchingProcessTests
{
    @Test
    public void call_simpleSearchingWord_shouldReturnWord()
    {
        SearchingProcess process = new SearchingProcess(
                new String[] {"igor", "kirill", "Alina"},
                "kirill"
        );

        Assertions.assertEquals(
                "kirill",
                process.call().getLine()
        );
    }

    @Test
    public void call_searchingWordWhereAbsentСoincidence_shouldReturnWord()
    {
        SearchingProcess process = new SearchingProcess(
                new String[] {"A", "SSSS", "ZZZZZZZZ"},
                "kirill"
        );

        Assertions.assertEquals(
                "kirill",
                process.call().getLine()
        );
    }

    @Test
    public void call_searchInEmptyArray_shouldReturnWord()
    {
        SearchingProcess process = new SearchingProcess(
                new String[] {},
                "kirill"
        );

        Assertions.assertEquals(
                "kirill",
                process.call().getLine()
        );
    }
}
