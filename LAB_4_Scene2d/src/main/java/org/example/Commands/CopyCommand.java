package org.example.Commands;

import org.example.Scene.Scene;

public class CopyCommand implements Command
{
    private final String oldName;
    private final String newName;

    public CopyCommand(String oldName, String newName)
    {
        this.oldName = oldName;
        this.newName = newName;
    }

    @Override
    public String friendlyResultMessage()
    {
        return "Figure " + oldName + " copy to " + newName;
    }

    @Override
    public void apply(Scene scene)
    {
        scene.copy(oldName, newName);
    }
}
