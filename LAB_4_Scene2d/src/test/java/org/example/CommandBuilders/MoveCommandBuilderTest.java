package org.example.CommandBuilders;

import org.example.Exceptions.BadFormatCommandException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MoveCommandBuilderTest
{
    @Test
    public void appendLine_inputCorrectLine_shouldNotThrowException()
    {
        MoveCommandBuilder builder = new MoveCommandBuilder();

        Assertions.assertDoesNotThrow(() -> builder.appendLine("move A1 (50, 50)"));
        Assertions.assertDoesNotThrow(() -> builder.appendLine("         move    A1 (  50       , 50  )  "));
    }

    @Test
    public void appendLine_inputInCorrectLineWithoutFigureName_shouldThrowException()
    {
        MoveCommandBuilder builder = new MoveCommandBuilder();
        String commandLine = "move  (50, 50)";

        BadFormatCommandException exception = Assertions.assertThrows(BadFormatCommandException.class,
                ()-> builder.appendLine(commandLine));

        Assertions.assertEquals("Command: { " + commandLine + " } has the wrong format in the " + MoveCommandBuilder.class.getName() +
                " { Problem in: figure name is empty }",
                exception.getMessage());
    }

    @Test
    public void appendLine_inputInCorrectLineWithoutMovingVector_shouldThrowException()
    {
        MoveCommandBuilder builder = new MoveCommandBuilder();
        String commandLine = "move A1";

        BadFormatCommandException exception = Assertions.assertThrows(BadFormatCommandException.class,
                ()-> builder.appendLine(commandLine));

        Assertions.assertEquals("Command: { " + commandLine + " } has the wrong format in the " + MoveCommandBuilder.class.getName() +
                " { Problem in: moving vector is empty }",
                exception.getMessage());
    }

    @Test
    public void isCommandReady_calledBeforeAppendLine_shouldReturnFalse()
    {
        MoveCommandBuilder builder = new  MoveCommandBuilder();
        Assertions.assertFalse(builder.isCommandReady());
    }

    @Test
    public void isCommandReady_calledAfterAnyThrowInClass_shouldReturnFalse()
    {
        MoveCommandBuilder builder = new  MoveCommandBuilder();

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
        MoveCommandBuilder builder = new  MoveCommandBuilder();
        builder.appendLine("move A1 (50, 50)");
        Assertions.assertTrue(builder.isCommandReady());
    }
}
