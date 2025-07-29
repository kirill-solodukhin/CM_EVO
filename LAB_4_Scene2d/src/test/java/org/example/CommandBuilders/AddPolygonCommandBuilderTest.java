package org.example.CommandBuilders;

import org.example.Exception.BadFormatCommandException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AddPolygonCommandBuilderTest
{
    @Test
    public void appendLine_correctInput_shouldNotThrowException()
    {
        AddPolygonCommandBuilder builder = new AddPolygonCommandBuilder();

        Assertions.assertDoesNotThrow(
                () -> builder.appendLine("add polygon HY1"));

        Assertions.assertDoesNotThrow(
                () -> builder.appendLine(" add point (200, 190)"));

        Assertions.assertDoesNotThrow(
                () -> builder.appendLine(" add point (   190   , 210  )  "));

        Assertions.assertDoesNotThrow(
                () -> builder.appendLine(" add point (   195   , 210  )  "));

        Assertions.assertDoesNotThrow(
                () -> builder.appendLine("  add        polygon      HY2   "));

        Assertions.assertDoesNotThrow(
                () -> builder.appendLine("  end polygon"));

        Assertions.assertDoesNotThrow(
                () -> builder.appendLine("  end        polygon       "));
    }

    @Test
    public void appendLine_inCorrectFormatAnUnknownCommand_shouldThrowException()
    {
        AddPolygonCommandBuilder builder = new AddPolygonCommandBuilder();

        String commandLine = "AnUnknownCommand";
        BadFormatCommandException exception = Assertions.assertThrows(BadFormatCommandException.class,
                () -> builder.appendLine(commandLine));

        Assertions.assertEquals(exception.getMessage(),"Command: { " + commandLine + " } has the wrong format in the " + AddPolygonCommandBuilder.class.getName() +
                " { an unknown error }");
    }

    @Test
    public void appendLine_inCorrectFormatWithoutName_shouldThrowException()
    {
        AddPolygonCommandBuilder builder = new AddPolygonCommandBuilder();

        String commandLine = "add polygon";
        BadFormatCommandException exception = Assertions.assertThrows(BadFormatCommandException.class,
                () -> builder.appendLine(commandLine));

        Assertions.assertEquals(exception.getMessage(),"Command: { " + commandLine + " } has the wrong format in the " + AddPolygonCommandBuilder.class.getName() +
                " { an unknown error }");
    }

    @Test
    public void appendLine_inCorrectFormatPointsCountLessTwo_shouldThrowException()
    {
        AddPolygonCommandBuilder builder = new AddPolygonCommandBuilder();
        String commandLine = "add polygon M1\nadd point (200, 190)\nadd point (190, 210)\nend polygon";

        builder.appendLine("add polygon M1");
        builder.appendLine("add point (200, 190)");
        builder.appendLine("add point (190, 210)");

        BadFormatCommandException exception = Assertions.assertThrows(BadFormatCommandException.class,
                () -> builder.appendLine("end polygon"));

        Assertions.assertEquals(exception.getMessage(),"Command: { " + commandLine + " } has the wrong format in the " + AddPolygonCommandBuilder.class.getName() +
                " { Problem in: points array size should be more 2 }");
    }

    @Test
    public void isCommandReady_calledBeforeAppendLine_shouldReturnFalse()
    {
        AddPolygonCommandBuilder builder = new AddPolygonCommandBuilder();
        Assertions.assertFalse(builder.isCommandReady());
    }

    @Test
    public void isCommandReady_calledAfterAnyThrowInClass_shouldReturnFalse()
    {
        AddPolygonCommandBuilder builder = new AddPolygonCommandBuilder();

        try
        {
            builder.appendLine("Bad line");
        }
        catch(BadFormatCommandException _)
        {}

        Assertions.assertFalse(builder.isCommandReady());
    }

    @Test
    public void isCommandReady_calledAfterFirestCommand_shouldReturnFalse()
    {
        AddPolygonCommandBuilder builder = new AddPolygonCommandBuilder();
        builder.appendLine("add polygon M1");

        Assertions.assertFalse(builder.isCommandReady());
    }

    @Test
    public void isCommandReady_calledAfterIntermediateCommand_shouldReturnFalse()
    {
        AddPolygonCommandBuilder builder = new AddPolygonCommandBuilder();
        builder.appendLine("add polygon M1");
        builder.appendLine("add point (200, 190)");

        Assertions.assertFalse(builder.isCommandReady());
    }

    @Test
    public void isCommandReady_calledAfterAllCommand_shouldReturnTrue()
    {
        AddPolygonCommandBuilder builder = new AddPolygonCommandBuilder();

        builder.appendLine("add polygon M1");
        builder.appendLine("add point (200, 190)");
        builder.appendLine("add point (190, 210)");
        builder.appendLine("add point (195, 210)");
        builder.appendLine("end polygon");

        Assertions.assertTrue(builder.isCommandReady());
    }
}
