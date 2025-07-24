package org.example.Commands;

import org.example.Scene.Scene;

import java.awt.*;

public class MoveCommand implements Command
{
    private final String name;
    private final Point vector;

    public MoveCommand(String name, Point vector)
    {
        this.name = name;
        this.vector = vector;
    }

    @Override
    public String friendlyResultMessage()
    {
        return "figure " + name + " is moved";
    }

    @Override
    public void apply(Scene scene)
    {
        scene.move(name, vector);
    }
}
