package org.example.Commands;

import org.example.Scene.Scene;

import java.util.List;

public class GroupCommand implements Command
{
    String groupName;
    List<String> figuresName;

    public GroupCommand(String groupName, List<String> figuresName)
    {
        this.groupName = groupName;
        this.figuresName = figuresName;
    }

    @Override
    public String friendlyResultMessage()
    {
        return "Figures " + figuresName + " group in " + groupName;
    }

    @Override
    public void apply(Scene scene)
    {
        scene.group(figuresName, groupName);
    }
}
