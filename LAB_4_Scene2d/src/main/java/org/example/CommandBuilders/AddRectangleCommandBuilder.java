package org.example.CommandBuilders;

import org.example.Commands.addFigureCommand;
import org.example.Commands.Command;
import org.example.Exception.BadFormatCommandException;
import org.example.Figure.Figure;
import org.example.Figure.RectangleFigure;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddRectangleCommandBuilder implements CommandBuilder
{
    private final Pattern pattern =
            Pattern.compile("(?<name>gle\\s*\\w+\\s)|(?<value>-?\\d+\\s*,\\s*-?\\d+\\s?)");

    private final List<Point> points = new ArrayList<>();
    private boolean isCommandReady = true;
    private Figure rectangle;
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
            if((findingLine = matcher.group("value")) != null)
            {
                String[] value = findingLine.split(",");

                points.add(new Point(
                        Integer.parseInt(value[0].trim()),
                        Integer.parseInt(value[1].trim())));

                continue;
            }

            if((findingLine = matcher.group("name")) != null)
            {
                findingLine = findingLine.substring(3, findingLine.length() - 1).trim();
                name = findingLine;
            }
        }

        ThrowIFBadCommand(commandLine);
        rectangle = new RectangleFigure(points.get(0), points.get(1));
    }

    @Override
    public Command getCommand()
    {
        return new addFigureCommand(name, rectangle);
    }

    @Override
    public void ThrowIFBadCommand(String commandLine)
    {
        if(!name.isEmpty() && !points.isEmpty())
        {
            return;
        }

        isCommandReady = false;
        throw new BadFormatCommandException("Command:" + commandLine + " have is bad format for " + AddRectangleCommandBuilder.class.getName());
    }
}
