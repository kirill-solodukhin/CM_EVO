package org.example.CommandBuilders;

import org.example.Commands.Command;
import org.example.Exception.CommandCannotBeRecognized;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandProducer implements CommandBuilder
{
    Map<Pattern, CommandBuilder> commands = new HashMap<>(Map.of(
            Pattern.compile(".*add rectangle.*"), new AddRectangleCommandBuilder(),
            Pattern.compile(".*add circle.*"), new AddCircleCommandBuilder(),
            Pattern.compile(".*add polygon.*"), new AddPolygonCommandBuilder()
    ));

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

        for(Map.Entry<Pattern, CommandBuilder> el: commands.entrySet())
        {
            matcher = el.getKey().matcher(commandLine);
            if(matcher.matches())
            {
                currentBuilder = el.getValue();
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
