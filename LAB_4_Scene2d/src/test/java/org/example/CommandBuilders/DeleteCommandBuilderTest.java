package org.example.CommandBuilders;

import org.example.Exceptions.BadFormatCommandException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DeleteCommandBuilderTest
{
    @Test
    public void appendLine_inputCorrectLine_shouldNotThrowException()
    {
        DeleteCommandBuilder builder = new DeleteCommandBuilder();
        Assertions.assertDoesNotThrow(() -> builder.appendLine("delete A1"));
    }

    @Test
    public void appendLine_inputInCorrectLine_shouldThrowException()
    {
        DeleteCommandBuilder builder = new DeleteCommandBuilder();
        BadFormatCommandException exception = Assertions.assertThrows(BadFormatCommandException.class,
                ()-> builder.appendLine("bad line"));
        
        Assertions.assertEquals("Command: { bad line } have is bad format for " + DeleteCommandBuilder.class.getName() +
                " { Problem in: input string is not look like pattern command }",
                exception.getMessage());
    }

    @Test
    public void isCommandReady_calledBeforeAppendLine_shouldReturnFalse()
    {
        DeleteCommandBuilder builder = new  DeleteCommandBuilder();
        Assertions.assertFalse(builder.isCommandReady());
    }

    @Test
    public void isCommandReady_calledAfterAnyThrowInClass_shouldReturnFalse()
    {
        DeleteCommandBuilder builder = new  DeleteCommandBuilder();

        try
        {
            builder.appendLine("Bad line");
        }
        catch(BadFormatCommandException _)
        {}

        Assertions.assertFalse(builder.isCommandReady());
    }

    @Test
    public void isCommandReady_calledAfterCorrectAppendLine_shouldReturnTrue()
    {
        DeleteCommandBuilder builder = new  DeleteCommandBuilder();
        builder.appendLine("delete f1");
        Assertions.assertTrue(builder.isCommandReady());
    }
}
