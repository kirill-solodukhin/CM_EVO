package org.example.CommandBuilders;

import org.example.Exception.BadFormatCommandException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AddRectangleCommandBuilderTest
{
    @Test
    public void appendLine_correctInput_shouldNotThrowException()
    {
        AddRectangleCommandBuilder builder = new AddRectangleCommandBuilder();

        Assertions.assertDoesNotThrow(
                () -> builder.appendLine("add rectangle M1 (110, 250) (290, 280)"));

        Assertions.assertDoesNotThrow(
                () -> builder.appendLine("  add   rectangle   M1    ( 110  , 250  )  (  290   ,   280  )  "));
    }

    @Test
    public void appendLine_inCorrectFormatWithoutName_shouldThrowException()
    {
        AddRectangleCommandBuilder builder = new  AddRectangleCommandBuilder();
        String commandLine = "add rectangle (110, 250) (290, 280)";

        BadFormatCommandException exception = Assertions.assertThrows(BadFormatCommandException.class,
                () -> builder.appendLine(commandLine));

        Assertions.assertEquals(exception.getMessage(),"Command: { " + commandLine + " } has the wrong format in the " + AddRectangleCommandBuilder.class.getName() +
                " { Problem in: name is empty }");
    }

    @Test
    public void appendLine_inCorrectFormatWithoutPoints_shouldThrowException()
    {
        AddRectangleCommandBuilder builder = new AddRectangleCommandBuilder();
        String commandLine = "add rectangle M1";

        BadFormatCommandException exception = Assertions.assertThrows(BadFormatCommandException.class,
                () -> builder.appendLine(commandLine));

        Assertions.assertEquals(exception.getMessage(),"Command: { " + commandLine + " } has the wrong format in the " + AddRectangleCommandBuilder.class.getName() +
                " { Problem in: points is empty }");
    }

    @Test
    public void appendLine_inCorrectFormatPointLessNull_shouldThrowException()
    {
        AddRectangleCommandBuilder builder = new  AddRectangleCommandBuilder();
        String commandLine = "add rectangle M1 (-110, 250) (290, 280)";

        BadFormatCommandException exception = Assertions.assertThrows(BadFormatCommandException.class,
                () -> builder.appendLine(commandLine));

        Assertions.assertEquals(exception.getMessage(), "Command: { " + commandLine + " } has the wrong format in the " + AddRectangleCommandBuilder.class.getName() +
                " { Problem in: point : [" + -110 + " , " + 250 + "] x or y must be more null }");
    }


    @Test
    public void isCommandReady_calledBeforeAppendLine_shouldReturnFalse()
    {
        AddRectangleCommandBuilder builder = new  AddRectangleCommandBuilder();
        Assertions.assertFalse(builder.isCommandReady());
    }

    @Test
    public void isCommandReady_calledAfterAnyThrowInClass_shouldReturnFalse()
    {
         AddRectangleCommandBuilder builder = new  AddRectangleCommandBuilder();

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
         AddRectangleCommandBuilder builder = new  AddRectangleCommandBuilder();
        builder.appendLine("add rectangle M1 (110, 250) (290, 280)");
        Assertions.assertTrue(builder.isCommandReady());
    }
}
