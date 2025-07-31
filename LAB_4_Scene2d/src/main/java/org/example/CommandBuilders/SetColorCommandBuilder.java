package org.example.CommandBuilders;

import org.example.Commands.Command;
import org.example.Commands.SetColorCommand;
import org.example.Exceptions.BadColorException;
import org.example.Exceptions.BadFormatCommandException;

import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SetColorCommandBuilder implements CommandBuilder
{
    private final Pattern pattern = Pattern.compile(
            "(?<color>\\(\\s*\\d+\\s*,\\s*\\d+\\s*,\\s*\\d+\\s*\\))" +
                    "|(?<name>to\\s+\\w+$)");

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
                color = parseColor(findingLine);
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
        if(color == null)
        {
            throw new BadFormatCommandException("Command: { " + commandLine + " } has the wrong format in the " +SetColorCommandBuilder.class.getName() +
                    " { Problem in: New figure color is empty }");
        }

        if(name == null || name.isEmpty())
        {
            throw new BadFormatCommandException("Command: { " + commandLine + " } has the wrong format in the " +SetColorCommandBuilder.class.getName() +
                " { Problem in: Figure name is empty }");
        }
    }

    private Color parseColor(String findingLine)
    {
        String[] values = findingLine.split(",");

        int red = Integer.parseInt(values[0].substring(1).trim());
        if(red > 255)
        {
            throw new BadColorException("Error in " + SetColorCommandBuilder.class.getName() +
                    " { Problem with color: colors value can not has value more 255, but your red color have: " + red + " }");
        }

        int green = Integer.parseInt(values[1].trim());
        if(green > 255)
        {
            throw new BadColorException("Error in " + SetColorCommandBuilder.class.getName() +
                    " { Problem with color: colors value can not has value more 255, but your green color have: " + green + " }");
        }

        int blue = Integer.parseInt(values[2].substring(0, values[2].length() - 1) .trim());
        if(blue > 255)
        {
            throw new BadColorException("Error in " + SetColorCommandBuilder.class.getName() +
                    " { Problem with color: colors value can not has value more 255, but your blue color have: " + blue + " }");
        }

        return new Color(red, green, blue);
    }
}
