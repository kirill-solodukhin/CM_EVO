package org.example.CommandBuilders;

import org.example.Exception.BadFormatCommandException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CopyCommandBuilderTest
{
    @Test
    public void appendLine_CorrectInput_ShouldNotThrowException()
    {
        CopyCommandBuilder builder = new CopyCommandBuilder();
        Assertions.assertDoesNotThrow(() -> builder.appendLine("copy scene to scene2"));
    }

    @Test
    public void appendLine_InCorrectInputWithoutOldName_ShouldThrowException()
    {
        CopyCommandBuilder builder = new CopyCommandBuilder();

        String commandLine = "copy  to scene2";
        Exception ex = Assertions.assertThrows(BadFormatCommandException.class,
                () -> builder.appendLine(commandLine));

        Assertions.assertEquals(ex.getMessage(), "Command: { " + commandLine + " } has the wrong format in the " + CopyCommandBuilder.class.getName() +
                " { Problem in: name of the copied figure is missing }");
    }

    @Test
    public void appendLine_InCorrectInputWithoutNewName_ShouldThrowException()
    {
        CopyCommandBuilder builder = new CopyCommandBuilder();

        String commandLine = "copy scene to";
        Exception ex = Assertions.assertThrows(BadFormatCommandException.class,
                () -> builder.appendLine(commandLine));

        Assertions.assertEquals(ex.getMessage(), "Command: { " + commandLine + " } has the wrong format in the " + CopyCommandBuilder.class.getName() +
                " { Problem in: name of the new figure is missing }");
    }

    @Test
    public void isCommandReady_calledBeforeAppendLine_shouldReturnFalse()
    {
        CopyCommandBuilder builder = new  CopyCommandBuilder();
        Assertions.assertFalse(builder.isCommandReady());
    }

    @Test
    public void isCommandReady_calledAfterAnyThrowInClass_shouldReturnFalse()
    {
        CopyCommandBuilder builder = new  CopyCommandBuilder();

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
        CopyCommandBuilder builder = new  CopyCommandBuilder();
        builder.appendLine("copy f1 to k1");
        Assertions.assertTrue(builder.isCommandReady());
    }
}
