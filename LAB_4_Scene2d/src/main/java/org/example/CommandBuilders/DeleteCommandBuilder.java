package org.example.CommandBuilders;

import org.example.Commands.Command;
import org.example.Commands.DeleteCommand;
import org.example.Exception.BadFormatCommandException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeleteCommandBuilder implements CommandBuilder
{
    private final Pattern pattern = Pattern.compile("delete\\s+\\w+$");
    private boolean isCommandReady = false;
    private String name;

    @Override
    public boolean isCommandReady()
    {
        return isCommandReady;
    }

    @Override
    public void appendLine(String commandLine)
    {
        Matcher matcher = pattern.matcher(commandLine);

        if(matcher.find())
        {
            name = matcher.group().trim();
            isCommandReady = true;
            return;
        }

        ThrowIFBadCommand(commandLine);
    }

    @Override
    public Command getCommand()
    {
        return new DeleteCommand(name);
    }

    @Override
    public void ThrowIFBadCommand(String commandLine)
    {
        throw new BadFormatCommandException("Command: { " + commandLine + " } have is bad format for " + DeleteCommandBuilder.class.getName() +
            " { Problem in: input string is not look like pattern command }");
    }
}
