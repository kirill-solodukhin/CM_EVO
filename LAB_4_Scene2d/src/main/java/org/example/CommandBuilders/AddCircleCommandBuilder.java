package org.example.CommandBuilders;

import org.example.Commands.Command;
import org.example.Commands.addFigureCommand;
import org.example.Exception.BadFormatCommandException;
import org.example.Figure.CircleFigure;
import org.example.Figure.Figure;

import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddCircleCommandBuilder implements CommandBuilder
{
    private final Pattern pattern =
            Pattern.compile("(?<name>cle\\s*\\w+\\s)|(?<center>-?\\d+\\s*,\\s*-?\\d+\\s?)|(?<radius> \\d+$)");

    private boolean isCommandReady = true;
    private Figure circle;
    private String name;
    private Point center;
    private int radius;

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
                name = findingLine.substring(3, findingLine.length() - 1).trim();
            }

            if((findingLine = matcher.group("center")) != null)
            {
                String[] value = findingLine.split(",");

                center = new Point(
                        Integer.parseInt(value[0].trim()),
                        Integer.parseInt(value[1].trim()));
            }

            if((findingLine = matcher.group("radius")) != null)
            {
                radius = Integer.parseInt(findingLine.trim());
            }
        }

        ThrowIFBadCommand(commandLine);
        circle = new CircleFigure(center, radius);
    }

    @Override
    public Command getCommand()
    {
        return new addFigureCommand(name, circle);
    }

    @Override
    public void ThrowIFBadCommand(String commandLine)
    {
        if(!name.isEmpty() && center != null && radius > 0)
        {
            return;
        }

        isCommandReady = false;
        throw new BadFormatCommandException("Command:" + commandLine + " have is bad format for " + AddCircleCommandBuilder.class.getName());
    }
}
