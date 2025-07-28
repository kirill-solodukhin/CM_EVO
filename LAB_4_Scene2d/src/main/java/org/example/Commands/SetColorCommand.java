package org.example.Commands;

import org.example.Scene.Scene;

import java.awt.*;

public class SetColorCommand implements Command
{
    private final String name;
    private final Color color;

    public SetColorCommand(String name, Color color)
    {
        this.name = name;
        this.color = color;
    }

    @Override
    public String friendlyResultMessage()
    {
        return "Figure " + name + " recolor in " + color.toString();
    }

    @Override
    public void apply(Scene scene)
    {
        scene.setColor(name, color);
    }
}
