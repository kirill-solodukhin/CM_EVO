package org.example.Commands;

import org.example.Scene.Scene;

public class DeleteCommand implements Command
{
    String name;

    public DeleteCommand(String name)
    {
        this.name = name;
    }

    @Override
    public String friendlyResultMessage()
    {
        return "Figure with " + name + " has been deleted";
    }

    @Override
    public void apply(Scene scene)
    {
        scene.delete(name);
    }
}
