import org.example.exceptions.UsersCountException;
import org.example.generator.Generator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GeneratorTests
{
    @Test
    public void generator_generateGenerateOneUser_shouldThrowException()
    {
       Assertions.assertThrows(UsersCountException.class,
               () -> new Generator(1, 1, 1));
    }

    @Test
    public void generator_generateFiveUser_shouldGenerate()
    {
        Generator generator = new Generator(5, 5, 5);

        Assertions.assertEquals(5, generator.getGenerateUser().get().size());
        Assertions.assertEquals(5, generator.getGenerateMessages().get().size());
        Assertions.assertEquals(5, generator.getGenerateFriendsRequests().get().size());
    }
}
