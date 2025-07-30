package org.example.CommandBuilders;

import org.example.Exception.BadColorException;
import org.example.Exception.BadFormatCommandException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SetColorCommandBuilderTest
{
    @Test
    public void appendLine_inputCorrectLine_shouldNotThrowException()
    {
        SetColorCommandBuilder builder = new SetColorCommandBuilder();

        Assertions.assertDoesNotThrow(() -> builder.appendLine("set color (20,100,100) to R1"));
        Assertions.assertDoesNotThrow(() -> builder.appendLine("  set    color   (20   , 31  ,100  ) to     R1      "));
    }

    @Test
    public void appendLine_inputInCorrectLineWithoutFigureName_shouldThrowException()
    {
        SetColorCommandBuilder builder = new SetColorCommandBuilder();
        String commandLine = "set color (20,100,100) to";

        BadFormatCommandException ex = Assertions.assertThrows(BadFormatCommandException.class,
                ()-> builder.appendLine(commandLine));

        Assertions.assertEquals("Command: { " + commandLine + " } has the wrong format in the " + SetColorCommandBuilder.class.getName() +
                        " { Problem in: Figure name is empty }",
                ex.getMessage());
    }

    @Test
    public void appendLine_inputInCorrectLineWithoutColor_shouldThrowException()
    {
        SetColorCommandBuilder builder = new SetColorCommandBuilder();
        String commandLine = "set color to R1";

        BadFormatCommandException ex = Assertions.assertThrows(BadFormatCommandException.class,
                ()-> builder.appendLine(commandLine));

        Assertions.assertEquals("Command: { " + commandLine + " } has the wrong format in the " +SetColorCommandBuilder.class.getName() +
                        " { Problem in: New figure color is empty }",
                ex.getMessage());
    }

    @Test
    public void appendLine_inputInCorrectLineWithBigColorValue_shouldThrowException()
    {
        SetColorCommandBuilder builder = new SetColorCommandBuilder();
        String commandLine = "set color (500,50,50) to R1";

        BadColorException ex = Assertions.assertThrows(BadColorException.class,
                ()-> builder.appendLine(commandLine));

        Assertions.assertEquals("Error in " + SetColorCommandBuilder.class.getName() +
                        " { Problem with color: colors value can not has value more 255, but your red color have: 500 }",
                ex.getMessage());
    }

    @Test
    public void isCommandReady_calledBeforeAppendLine_shouldReturnFalse()
    {
        SetColorCommandBuilder builder = new  SetColorCommandBuilder();
        Assertions.assertFalse(builder.isCommandReady());
    }

    @Test
    public void isCommandReady_calledAfterAnyThrowInClass_shouldReturnFalse()
    {
        SetColorCommandBuilder builder = new  SetColorCommandBuilder();

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
        SetColorCommandBuilder builder = new  SetColorCommandBuilder();
        builder.appendLine("set color (20,100,100) to R1");
        Assertions.assertTrue(builder.isCommandReady());
    }
}
