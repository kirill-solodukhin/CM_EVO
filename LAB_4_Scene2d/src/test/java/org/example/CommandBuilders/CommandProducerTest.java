package org.example.CommandBuilders;

import org.example.Commands.AddFigureCommand;
import org.example.Exception.CommandCannotBeRecognized;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CommandProducerTest
{
    @Test
    public void appendLine_inCorrectInput_shouldThrowException()
    {
        CommandProducer commandProducer = new CommandProducer();
        Assertions.assertThrows(CommandCannotBeRecognized.class,
                () -> commandProducer.appendLine("badLine"));
    }

    @Test
    public void  appendLine_correctInput_shouldNotThrowException()
    {
        CommandProducer commandProducer = new CommandProducer();
        Assertions.assertDoesNotThrow(
                () -> commandProducer.appendLine("add circle BASE (200,200) radius 200"));
    }

    @Test
    public void isCommandReady_correctInputAndCommIsReady_shouldReturnTrue()
    {
        CommandProducer commandProducer = new CommandProducer();
        commandProducer.appendLine("add circle BASE (200,200) radius 200");
        Assertions.assertTrue(commandProducer.isCommandReady());
    }

    @Test
    public void isCommandReady_correctInputAndCommIsNotReady_shouldReturnFalse()
    {
        CommandProducer commandProducer = new CommandProducer();
        commandProducer.appendLine("add polygon HY1");
        commandProducer.appendLine("add point (200, 190)");
        Assertions.assertFalse(commandProducer.isCommandReady());
    }

    @Test
    public void getCommand_inputIsAddRect_shouldReturnAddFigureCommand()
    {
        CommandProducer commandProducer = new CommandProducer();
        commandProducer.appendLine("add rectangle M1 (110, 250) (290, 280)");
        Assertions.assertInstanceOf(AddFigureCommand.class, commandProducer.getCommand());
    }
}
