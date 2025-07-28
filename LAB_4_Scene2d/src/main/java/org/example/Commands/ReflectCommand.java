package org.example.Commands;

import org.example.Commands.Supportive.ReflectOrientation;
import org.example.Scene.Scene;

public class ReflectCommand implements Command
{
    String name;
    ReflectOrientation orientation;

    public ReflectCommand(String name, ReflectOrientation orientation)
    {
        this.name = name;
        this.orientation = orientation;
    }

    @Override
    public String friendlyResultMessage()
    {
        return "Figure " + name + " is reflect to " + orientation;
    }

    @Override
    public void apply(Scene scene)
    {
        scene.reflect(name, orientation);
    }
}
