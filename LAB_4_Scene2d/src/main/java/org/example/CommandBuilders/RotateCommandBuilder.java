package org.example.CommandBuilders;

import org.example.Commands.Command;
import org.example.Commands.RotateCommand;
import org.example.Exceptions.BadFormatCommandException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RotateCommandBuilder implements CommandBuilder
{
    private final Pattern pattern =
            Pattern.compile("(?<name>rotate\\s+[a-zA-Z]+\\d*\\s*)|(?<value>-?\\d+$)");

    private boolean isCommandReady = false;
    private String name;
    private Integer angle;

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
        if(name == null || name.isEmpty())
        {
            throw new BadFormatCommandException("Command: { " + commandLine + " } has the wrong format in the " + RotateCommandBuilder.class.getName() +
                    " { Problem in: Figure name is empty }");
        }

        if(angle == null)
        {

            throw new BadFormatCommandException("Command: { " + commandLine + " } has the wrong format in the " + RotateCommandBuilder.class.getName() +
                    " { Problem in: Rotation angle is empty }");
        }
    }
}
