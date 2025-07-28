package org.example.CommandBuilders;

import org.example.Commands.Command;
import org.example.Exception.CommandCannotBeRecognized;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandProducer implements CommandBuilder
{
    private final Map<Pattern, Supplier<CommandBuilder>> commands = new HashMap<>();

    {
        commands.put(Pattern.compile("^add rectangle.*"), AddRectangleCommandBuilder::new);
        commands.put(Pattern.compile("^add circle.*"), AddCircleCommandBuilder::new);
        commands.put(Pattern.compile("^add polygon.*"), AddPolygonCommandBuilder::new);
        commands.put(Pattern.compile("^move.*"), MoveCommandBuilder::new);
        commands.put(Pattern.compile("^rotate.*"), RotateCommandBuilder::new);
        commands.put(Pattern.compile("^reflect.*"), ReflectCommandBuilder::new);
        commands.put(Pattern.compile("^delete.*") , DeleteCommandBuilder::new);
        commands.put(Pattern.compile("^copy.*"), CopyCommandBuilder::new);
        commands.put(Pattern.compile("^set.*"), SetColorCommandBuilder::new);
    }

    private CommandBuilder currentBuilder;

    @Override
    public boolean isCommandReady()
    {
        if(currentBuilder == null)
        {
            return false;
        }

        return currentBuilder.isCommandReady();
    }

    @Override
    public void appendLine(String commandLine)
    {
        if(currentBuilder == null)
        {
           findBuilder(commandLine);
           ThrowIFBadCommand(commandLine);
        }

        currentBuilder.appendLine(commandLine);
    }

    @Override
    public Command getCommand()
    {
        if (currentBuilder == null)
        {
            return null;
        }

        Command command = currentBuilder.getCommand();
        currentBuilder = null;

        return command;
    }

    private void findBuilder(String commandLine)
    {
        Matcher matcher;

        for(Map.Entry<Pattern, Supplier<CommandBuilder>> el: commands.entrySet())
        {
            matcher = el.getKey().matcher(commandLine);
            if(matcher.matches())
            {
                currentBuilder = el.getValue().get();
                break;
            }
        }
    }

    @Override
    public void ThrowIFBadCommand(String commandLine)
    {
        if(currentBuilder != null)
        {
            return;
        }

        throw new CommandCannotBeRecognized(commandLine + " command is not recognized");
    }
}
