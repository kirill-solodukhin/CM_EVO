package org.example.CommandBuilders;

import org.example.Commands.Command;
import org.example.Commands.AddFigureCommand;
import org.example.Exceptions.BadFormatCommandException;
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

    private final StringBuilder allCommand = new StringBuilder();
    private final List<Point> points = new ArrayList<>();
    private boolean isCommandReady = false;
    private Figure polygon;
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

        if(!matcher.find())
        {
            throw new BadFormatCommandException("Command: { " + commandLine + " } has the wrong format in the " + AddPolygonCommandBuilder.class.getName() +
                    " { an unknown error }");
        }

        if((findingLine = matcher.group("name")) != null)
        {
            name = findingLine.substring(7).trim();
            allCommand.append(commandLine).append('\n');
            return;
        }

        if((findingLine = matcher.group("value")) != null)
        {
            String[] value = findingLine.split(",");

            points.add(new Point(
                    Integer.parseInt(value[0].trim()),
                    Integer.parseInt(value[1].trim())
            ));

            allCommand.append(commandLine).append('\n');
            return;
        }

        if((findingLine = matcher.group("end")) != null)
        {
            isCommandReady = true;
            allCommand.append(commandLine);
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
        if(points.size() <= 2)
        {
            throw new BadFormatCommandException("Command: { " + command + " } has the wrong format in the " + AddPolygonCommandBuilder.class.getName() +
                    " { Problem in: points array size should be more 2 }");
        }

        for(Point p : points)
        {
            if(p.x >= 0 && p.y >= 0)
            {
                continue;
            }

            throw new BadFormatCommandException("Command: { " + command + " } has the wrong format in the " + AddPolygonCommandBuilder.class.getName() +
                    " { Problem in: point : [" + p.x + " , " + p.y + "] x or y must be more null }");
        }
    }
}
