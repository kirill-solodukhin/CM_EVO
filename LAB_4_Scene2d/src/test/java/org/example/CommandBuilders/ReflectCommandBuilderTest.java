package org.example.CommandBuilders;

import org.example.Exception.BadFormatCommandException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ReflectCommandBuilderTest
{
    @Test
    public void appendLine_inputCorrectLine_shouldNotThrowException()
    {
        ReflectCommandBuilder builder = new ReflectCommandBuilder();

        Assertions.assertDoesNotThrow(() -> builder.appendLine("reflect horizontally scene"));
        Assertions.assertDoesNotThrow(() -> builder.appendLine("   reflect     horizontally     scene   "));
        Assertions.assertDoesNotThrow(() -> builder.appendLine("reflect vertically scene"));
        Assertions.assertDoesNotThrow(() -> builder.appendLine("   reflect     vertically    scene   "));
    }

    @Test
    public void appendLine_inputInCorrectLineWithoutFigureName_shouldThrowException()
    {
        ReflectCommandBuilder builder = new ReflectCommandBuilder();
        String commandLine = "reflect horizontally";

        BadFormatCommandException ex = Assertions.assertThrows(BadFormatCommandException.class,
                ()-> builder.appendLine(commandLine));

        Assertions.assertEquals("Command: { " + commandLine + " } has the wrong format in the " + ReflectCommandBuilder.class.getName() +
                " { Problem in: figure name is empty }",
                ex.getMessage());
    }

    @Test
    public void appendLine_inputInCorrectLineWithoutReflectOrientation_shouldThrowException()
    {
        ReflectCommandBuilder builder = new ReflectCommandBuilder();
        String commandLine = "reflect scene";

        BadFormatCommandException ex = Assertions.assertThrows(BadFormatCommandException.class,
                ()-> builder.appendLine(commandLine));

        Assertions.assertEquals("Command: { " + commandLine + " } has the wrong format in the " + ReflectCommandBuilder.class.getName() +
                " { Problem in: Orientation for reflect not be found }",
                ex.getMessage());
    }

    @Test
    public void isCommandReady_calledBeforeAppendLine_shouldReturnFalse()
    {
        ReflectCommandBuilder builder = new  ReflectCommandBuilder();
        Assertions.assertFalse(builder.isCommandReady());
    }

    @Test
    public void isCommandReady_calledAfterAnyThrowInClass_shouldReturnFalse()
    {
        ReflectCommandBuilder builder = new  ReflectCommandBuilder();

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
        ReflectCommandBuilder builder = new  ReflectCommandBuilder();
        builder.appendLine("reflect horizontally scene");
        Assertions.assertTrue(builder.isCommandReady());
    }
}
