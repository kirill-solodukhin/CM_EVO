package org.example.CommandBuilders;

import org.example.Commands.Command;
import org.example.Commands.AddFigureCommand;
import org.example.Exception.BadFormatCommandException;
import org.example.Figure.CircleFigure;
import org.example.Figure.Figure;

import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddCircleCommandBuilder implements CommandBuilder
{
    private final Pattern pattern =
            Pattern.compile("(?<name>cle\\s*\\w+\\s)|(?<center>-?\\d+\\s*,\\s*-?\\d+\\s?)|(?<radius> -?\\d+$)");

    private boolean isCommandReady = false;
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
                continue;
            }

            if((findingLine = matcher.group("center")) != null)
            {
                String[] value = findingLine.split(",");

                center = new Point(
                        Integer.parseInt(value[0].trim()),
                        Integer.parseInt(value[1].trim()));

                continue;
            }

            if((findingLine = matcher.group("radius")) != null)
            {
                radius = Integer.parseInt(findingLine.trim());
            }
        }

        ThrowIFBadCommand(commandLine);
        isCommandReady = true;
        circle = new CircleFigure(center, radius);
    }

    @Override
    public Command getCommand()
    {
        return new AddFigureCommand(name, circle);
    }

    @Override
    public void ThrowIFBadCommand(String commandLine)
    {
        if(name == null)
        {
            throw new BadFormatCommandException("Command: { " + commandLine + " } has the wrong format in the " + AddCircleCommandBuilder.class.getName() +
                    " { Problem in: name is empty }");
        }

        if(center == null)
        {
            throw new BadFormatCommandException("Command: { " + commandLine + " } has the wrong format in the " + AddCircleCommandBuilder.class.getName() +
                    " { Problem in: center point does not exist }");
        }

        if(center.x <= radius || center.y <= radius)
        {
            throw new BadFormatCommandException("Command: { " + commandLine + " } has the wrong format in the " + AddCircleCommandBuilder.class.getName() +
                    " { Problem in: center point cannot be closer to zero than the radius }");
        }

        if(radius <= 0)
        {
            throw new BadFormatCommandException("Command: { " + commandLine + " } have is bad format for " + AddCircleCommandBuilder.class.getName() +
                    " { Problem in: radius must be more than null }");
        }
    }
}
