package org.example.CommandBuilders;

import org.example.Commands.Command;
import org.example.Commands.SetColorCommand;
import org.example.Exception.BadFormatCommandException;

import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SetColorCommandBuilder implements CommandBuilder
{
    // set color {(red, green, blue) | color name} to {имя фигуры}|scene
    private final Pattern pattern = Pattern.compile(
            "(?<color>\\(\\s*\\d+\\s*,\\s*\\d+\\s*,\\s*\\d+\\s*\\))" +
                    "|(?<name>to\\s+\\w+\\d*$)");

    private boolean isCommandReady = false;
    private String name;
    private Color color;

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
            if((findingLine = matcher.group("color")) != null)
            {
                String[] values = findingLine.split(",");

                color = new Color(
                        Integer.parseInt(values[0].substring(1).trim()),
                        Integer.parseInt(values[1].trim()),
                        Integer.parseInt(values[2].substring(0, values[2].length() - 1) .trim())
                );
                continue;
            }

            if((findingLine = matcher.group("name")) != null)
            {
                name = findingLine.substring(2).trim();
            }
        }

        ThrowIFBadCommand(commandLine);
        isCommandReady = true;
    }

    @Override
    public Command getCommand()
    {
        return new SetColorCommand(name, color);
    }

    @Override
    public void ThrowIFBadCommand(String commandLine)
    {
        if(color != null && !name.isEmpty())
        {
            return;
        }

        throw new BadFormatCommandException("Command:" + commandLine +
                " have is bad format for " + SetColorCommandBuilder.class.getName());
    }
}
