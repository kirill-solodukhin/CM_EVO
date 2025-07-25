package org.example.Commands;

import org.example.Scene.Scene;

public class RotateCommand implements Command
{
    private final String name;
    private final double angle;

    public RotateCommand(String name, double angle)
    {
        this.name = name;
        this.angle = angle;
    }

    @Override
    public String friendlyResultMessage()
    {
        return "figure " + name + " is rotate";
    }

    @Override
    public void apply(Scene scene)
    {
        scene.rotate(name, angle);
    }
}
