package org.example.CommandBuilders;

import org.example.Commands.Command;
import org.example.Commands.ReflectCommand;
import org.example.Commands.Supportive.ReflectOrientation;
import org.example.Exception.BadFormatCommandException;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReflectCommandBuilder implements CommandBuilder
{
    private final Pattern pattern =
            Pattern.compile("(?<orientation>vertically|horizontally)|(?<name>\\s\\w+\\d?\\s*$)");

    private boolean isCommandReady = false;
    private ReflectOrientation orientation;
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

        String findingLine;
        while (matcher.find())
        {
            if((findingLine = matcher.group("orientation")) != null)
            {
                orientation = Objects.equals(findingLine, "vertically") ?
                        ReflectOrientation.Vertical : ReflectOrientation.Horizontal;

                continue;
            }

            if((findingLine = matcher.group("name")) != null)
            {
                name = findingLine.trim();
            }
        }

        ThrowIFBadCommand(commandLine);
        isCommandReady = true;
    }

    @Override
    public Command getCommand()
    {
        return new ReflectCommand(name, orientation);
    }

    @Override
    public void ThrowIFBadCommand(String commandLine)
    {
        if(orientation != null && !name.isEmpty())
        {
            return;
        }

        throw new BadFormatCommandException("Command:" + commandLine +
                " have is bad format for " +  ReflectCommandBuilder.class.getName());
    }
}
