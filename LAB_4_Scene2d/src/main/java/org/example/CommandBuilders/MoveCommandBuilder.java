package org.example.CommandBuilders;

import org.example.Commands.Command;
import org.example.Commands.MoveCommand;
import org.example.Exception.BadFormatCommandException;

import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MoveCommandBuilder implements CommandBuilder
{
    private final Pattern pattern =
            Pattern.compile("(?<name>move\\s\\w+\\d?\\s+)|(?<value>-?\\d+\\s*,\\s*-?\\d+\\s?)");

    private boolean isCommandReady = false;
    private String name;
    private Point vector;

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
        while(matcher.find())
        {
            if((findingLine = matcher.group("name")) != null)
            {
                name = findingLine.substring(5).trim();
            }

            if((findingLine = matcher.group("value")) != null)
            {
                String[] value = findingLine.split(",");

                vector = new Point(
                        Integer.parseInt(value[0].trim()),
                        Integer.parseInt(value[1].trim())
                );
            }
        }

        ThrowIFBadCommand(commandLine);
    }

    @Override
    public Command getCommand()
    {
        return new MoveCommand(name, vector);
    }

    @Override
    public void ThrowIFBadCommand(String commandLine)
    {
        if(!name.isEmpty() && vector != null)
        {
            isCommandReady = true;
            return;
        }

        isCommandReady = false;
        throw new BadFormatCommandException("Command:" + commandLine + " have is bad format for " +  MoveCommandBuilder.class.getName());
    }
}
