package org.example.CommandBuilders;

import org.example.Exception.BadFormatCommandException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RotateCommandBuilderTest
{
    @Test
    public void appendLine_inputCorrectLine_shouldNotThrowException()
    {
        RotateCommandBuilder builder = new RotateCommandBuilder();

        Assertions.assertDoesNotThrow(() -> builder.appendLine("rotate scene 90"));
        Assertions.assertDoesNotThrow(() -> builder.appendLine("   rotate    scene   90   "));
    }

    @Test
    public void appendLine_inputInCorrectLineWithoutFigureName_shouldThrowException()
    {
        RotateCommandBuilder builder = new RotateCommandBuilder();
        String commandLine = "rotate 90";

        BadFormatCommandException ex = Assertions.assertThrows(BadFormatCommandException.class,
                ()-> builder.appendLine(commandLine));

        Assertions.assertEquals("Command: { " + commandLine + " } has the wrong format in the " + RotateCommandBuilder.class.getName() +
                        " { Problem in: Figure name is empty }",
                ex.getMessage());
    }

    @Test
    public void appendLine_inputInCorrectLineWithoutRotationAngle_shouldThrowException()
    {
        RotateCommandBuilder builder = new RotateCommandBuilder();
        String commandLine = "rotate scene";

        BadFormatCommandException ex = Assertions.assertThrows(BadFormatCommandException.class,
                ()-> builder.appendLine(commandLine));

        Assertions.assertEquals("Command: { " + commandLine + " } has the wrong format in the " + RotateCommandBuilder.class.getName() +
                        " { Problem in: Rotation angle is empty }",
                ex.getMessage());
    }

    @Test
    public void isCommandReady_calledBeforeAppendLine_shouldReturnFalse()
    {
        RotateCommandBuilder builder = new  RotateCommandBuilder();
        Assertions.assertFalse(builder.isCommandReady());
    }

    @Test
    public void isCommandReady_calledAfterAnyThrowInClass_shouldReturnFalse()
    {
        RotateCommandBuilder builder = new  RotateCommandBuilder();

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
        RotateCommandBuilder builder = new  RotateCommandBuilder();
        builder.appendLine("rotate scene 90");
        Assertions.assertTrue(builder.isCommandReady());
    }
}
