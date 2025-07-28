package org.example.CommandBuilders;

import org.example.Commands.Command;
import org.example.Commands.CopyCommand;
import org.example.Exception.BadFormatCommandException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CopyCommandBuilder implements CommandBuilder
{
    //copy {имя}|scene to {имя_копии}
    Pattern pattern = Pattern.compile("(?<oldName>copy\\s+\\w+\\d*\\s+)|(?<newName>to.*$)");
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

        String findingLine;
        while (matcher.find())
        {
            if((findingLine = matcher.group("oldName")) != null)
            {
                oldName = findingLine.substring(4).trim();
                continue;
            }

            if((findingLine = matcher.group("newName")) != null)
            {
                newName = findingLine.substring(2).trim();
            }
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
        if(!oldName.isEmpty() && !newName.isEmpty())
        {
            return;
        }

        throw new BadFormatCommandException("Command:" + commandLine +
                " have is bad format for " + CopyCommandBuilder.class.getName());
    }
}
