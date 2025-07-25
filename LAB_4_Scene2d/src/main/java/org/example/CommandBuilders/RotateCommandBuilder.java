package org.example.CommandBuilders;

import org.example.Commands.Command;
import org.example.Commands.RotateCommand;
import org.example.Exception.BadFormatCommandException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RotateCommandBuilder implements CommandBuilder
{
    private final Pattern pattern =
            Pattern.compile("(?<name>rotate\\s\\w+\\d?\\s+)|(?<value>-?\\d+$)");

    private boolean isCommandReady = false;
    private String name;
    private int angle;

    @Override
    public boolean isCommandReady()
    {
        return isCommandReady;
    }

    @Override
    public void appendLine(String commandLine)
    {
        Matcher matcher = pattern.matcher(commandLine);

        String findingLine;
        while (matcher.find())
        {
            if((findingLine = matcher.group("name")) != null)
            {
                name = findingLine.substring(6).trim();
            }

            if((findingLine = matcher.group("value")) != null)
            {
                angle = Integer.parseInt(findingLine.trim());
            }
        }

        ThrowIFBadCommand(commandLine);
        isCommandReady = true;
    }

    @Override
    public Command getCommand()
    {
        return new RotateCommand(name, angle);
    }

    @Override
    public void ThrowIFBadCommand(String commandLine)
    {
        if(!name.isEmpty())
        {
            return;
        }

        throw new BadFormatCommandException("Command:" + commandLine +
                " have is bad format for " +  RotateCommandBuilder.class.getName());
    }
}
