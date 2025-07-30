package org.example.CommandBuilders;

import org.example.Commands.Command;
import org.example.Commands.GroupCommand;
import org.example.Exception.BadFormatCommandException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GroupCommandBuilder implements CommandBuilder
{
    //group {имя1}, … {имяN} as {имя группы}
    private final Pattern pattern = Pattern.compile("(?<name>group.*\\s+as)|(?<groupName>\\s+\\w+\\d*$)");
    private final List<String> figuresNames = new ArrayList<>();
    private String groupName;
    private boolean isCommandReady = false;


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
                String[] names = findingLine.trim().substring(5, findingLine.length() - 3).split(",");

                for (String name : names)
                {
                    figuresNames.add(name.trim());
                }

                continue;
            }

            if((findingLine = matcher.group("groupName")) != null)
            {
                groupName = findingLine.trim();
                break;
            }
        }

        ThrowIFBadCommand(commandLine);
        isCommandReady = true;
    }

    @Override
    public Command getCommand()
    {
        return new GroupCommand(groupName, figuresNames);
    }

    @Override
    public void ThrowIFBadCommand(String commandLine)
    {
        if(groupName == null || groupName.isEmpty())
        {
            throw new BadFormatCommandException("Command: { " + commandLine + " } has the wrong format in the " + GroupCommandBuilder.class.getName() +
                    " { Problem in: group name not be found }");
        }

        if(figuresNames.size() < 2)
        {
            throw new BadFormatCommandException("Command: { " + commandLine + " } has the wrong format in the " + GroupCommandBuilder.class.getName() +
                    " { Problem in: figures count should be more 1 }");
        }

    }
}
