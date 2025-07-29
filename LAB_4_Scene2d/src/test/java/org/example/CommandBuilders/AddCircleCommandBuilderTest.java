package org.example.CommandBuilders;

import org.example.Exception.BadFormatCommandException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AddCircleCommandBuilderTest
{
    @Test
    public void appendLine_correctInput_shouldNotThrowException()
    {
        AddCircleCommandBuilder builder = new AddCircleCommandBuilder();

        Assertions.assertDoesNotThrow(
                () -> builder.appendLine("add circle Y1 (130,150) radius 20"));

        Assertions.assertDoesNotThrow(
                () -> builder.appendLine("  add    circle  Y1  (   130   ,   150   )  radius    20  "));
    }

    @Test
    public void appendLine_inCorrectFormatWithoutName_shouldThrowException()
    {
        AddCircleCommandBuilder builder = new AddCircleCommandBuilder();
        String commandLine = "add circle (130,150) radius 20";

        BadFormatCommandException exception = Assertions.assertThrows(BadFormatCommandException.class,
                () -> builder.appendLine(commandLine));

        Assertions.assertEquals(exception.getMessage(),"Command: { " + commandLine + " } has the wrong format in the " + AddCircleCommandBuilder.class.getName() +
                " { Problem in: name is empty }");
    }

    @Test
    public void appendLine_inCorrectFormatWithoutCenterPoint_shouldThrowException()
    {
        AddCircleCommandBuilder builder = new AddCircleCommandBuilder();
        String commandLine = "add circle Y1 radius 20";

        BadFormatCommandException exception = Assertions.assertThrows(BadFormatCommandException.class,
                () -> builder.appendLine(commandLine));

        Assertions.assertEquals(exception.getMessage(),"Command: { " + commandLine + " } has the wrong format in the " + AddCircleCommandBuilder.class.getName() +
                " { Problem in: center point does not exist }");
    }

    @Test
    public void appendLine_inCorrectFormatRadiusMoreCenterX_shouldThrowException()
    {
        AddCircleCommandBuilder builder = new AddCircleCommandBuilder();
        String commandLine = "add circle Y1 (130, 250) radius 200";

        BadFormatCommandException exception = Assertions.assertThrows(BadFormatCommandException.class,
                () -> builder.appendLine(commandLine));

        Assertions.assertEquals(exception.getMessage(), "Command: { " + commandLine + " } has the wrong format in the " + AddCircleCommandBuilder.class.getName() +
                " { Problem in: center point cannot be closer to zero than the radius }");
    }

    @Test
    public void appendLine_inCorrectFormatRadiusMoreCenterY_shouldThrowException()
    {
        AddCircleCommandBuilder builder = new AddCircleCommandBuilder();
        String commandLine = "add circle Y1 (230, 50) radius 100";

        BadFormatCommandException exception = Assertions.assertThrows(BadFormatCommandException.class,
                () -> builder.appendLine(commandLine));

        Assertions.assertEquals(exception.getMessage(), "Command: { " + commandLine + " } has the wrong format in the " + AddCircleCommandBuilder.class.getName() +
                " { Problem in: center point cannot be closer to zero than the radius }");
    }

    @Test
    public void appendLine_inCorrectFormatRadiusLessNull_ThrowException()
    {
        AddCircleCommandBuilder builder = new AddCircleCommandBuilder();
        String commandLine = "add circle Y1 (230, 50) radius -100";

        BadFormatCommandException exception = Assertions.assertThrows(BadFormatCommandException.class,
                () -> builder.appendLine(commandLine));

        Assertions.assertEquals(exception.getMessage(),  "Command: { " + commandLine + " } have is bad format for " + AddCircleCommandBuilder.class.getName() +
                " { Problem in: radius must be more than null }");
    }

    @Test
    public void isCommandReady_calledBeforeAppendLine_shouldReturnFalse()
    {
        AddCircleCommandBuilder builder = new AddCircleCommandBuilder();
        Assertions.assertFalse(builder.isCommandReady());
    }

    @Test
    public void isCommandReady_calledAfterAnyThrowInClass_shouldReturnFalse()
    {
        AddCircleCommandBuilder builder = new AddCircleCommandBuilder();

        try
        {
            builder.appendLine("add circle Y1 (130,150) radius");
        }
        catch(BadFormatCommandException _)
        {}

        Assertions.assertFalse(builder.isCommandReady());
    }

    @Test
    public void isCommandReady_calledAfterCorrectAppendLine_shouldReturnTrue()
    {
        AddCircleCommandBuilder builder = new AddCircleCommandBuilder();
        builder.appendLine("add circle Y1 (130,150) radius 20");
        Assertions.assertTrue(builder.isCommandReady());
    }
}
