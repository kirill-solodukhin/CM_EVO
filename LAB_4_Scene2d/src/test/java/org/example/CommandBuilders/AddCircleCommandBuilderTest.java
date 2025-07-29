package org.example.CommandBuilders;

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
}
