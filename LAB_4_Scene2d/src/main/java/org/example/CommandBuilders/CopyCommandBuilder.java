package org.example.CommandBuilders;

import org.example.Commands.Command;
import org.example.Commands.CopyCommand;
import org.example.Exception.BadFormatCommandException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CopyCommandBuilder implements CommandBuilder
{
    Pattern pattern = Pattern.compile("copy\\s*\\w*\\s*to\\s*\\w*\\s*$");
    private boolean isCommandReady = false;
    private String oldName;
    private String newName;

    @Override
    public boolean isCommandReady()
    {
        return isCommandReady;
    }

    @Override
    public void appendLine(String commandLine)
    {
        Matcher matcher = pattern.matcher(commandLine);
        String[] findingLines;

        if(matcher.matches())
        {
            findingLines = matcher.group().split("to");

            oldName = findingLines.length > 0 ? findingLines[0].trim().substring(4).trim() : null;
            newName = findingLines.length > 1 ? findingLines[1].trim() : null;
        }

        ThrowIFBadCommand(commandLine);
        isCommandReady = true;
    }

    @Override
    public Command getCommand()
    {
        return new CopyCommand(oldName, newName);
    }

    @Override
    public void ThrowIFBadCommand(String commandLine)
    {
        if(oldName == null || oldName.isEmpty())
        {
            throw new BadFormatCommandException("Command: { " + commandLine + " } has the wrong format in the " + CopyCommandBuilder.class.getName() +
                    " { Problem in: name of the copied figure is missing }");
        }

        if(newName == null || newName.isEmpty())
        {
            throw new BadFormatCommandException("Command: { " + commandLine + " } has the wrong format in the " + CopyCommandBuilder.class.getName() +
                    " { Problem in: name of the new figure is missing }");
        }
    }
}