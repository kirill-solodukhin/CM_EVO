package org.example.CommandBuilders;

import org.example.Commands.Command;
import org.example.Commands.AddFigureCommand;
import org.example.Exception.BadFormatCommandException;
import org.example.Figure.Figure;
import org.example.Figure.PolygonFigure;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddPolygonCommandBuilder implements CommandBuilder
{
    private final Pattern pattern =
            Pattern.compile("(?<name>polygon\\s+.*$)|(?<value>-?\\d+\\s*,\\s*-?\\d+\\s?)|(?<end>end polygon)");

    StringBuilder allCommand = new StringBuilder();
    private boolean isCommandReady = false;
    List<Point> points = new ArrayList<>();
    Figure polygon;
    String name;

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

        if(!matcher.find())
        {
            throw new BadFormatCommandException("Command:" + commandLine + " have is bad format for " + AddPolygonCommandBuilder.class.getName());
        }

        if((findingLine = matcher.group("name")) != null)
        {
            name = findingLine.substring(7).trim();
            allCommand.append(findingLine);
            return;
        }

        if((findingLine = matcher.group("value")) != null)
        {
            String[] value = findingLine.split(",");

            points.add(new Point(
                    Integer.parseInt(value[0].trim()),
                    Integer.parseInt(value[1].trim())
            ));

            allCommand.append(findingLine);
            return;
        }

        if((findingLine = matcher.group("end")) != null)
        {
            isCommandReady = true;
            allCommand.append(findingLine);
        }

        ThrowIFBadCommand(allCommand.toString());
        polygon = new PolygonFigure(points);
    }

    @Override
    public Command getCommand()
    {
        return new AddFigureCommand(name, polygon);
    }

    @Override
    public void ThrowIFBadCommand(String command)
    {
        if(!name.isEmpty() && !points.isEmpty())
        {
            return;
        }

        throw new BadFormatCommandException("Command:" + command + " have is bad format for " + AddPolygonCommandBuilder.class.getName());
    }
}
