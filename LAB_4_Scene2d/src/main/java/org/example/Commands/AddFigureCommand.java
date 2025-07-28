package org.example.Commands;

import org.example.Figure.Figure;
import org.example.Scene.Scene;

public class AddFigureCommand implements Command
{
    private final String name;
    private final Figure figure;

    public AddFigureCommand(String name, Figure figure)
    {
        this.name = name;
        this.figure = figure;
    }

    @Override
    public String friendlyResultMessage()
    {
        return "added figure " + name + " of type " + figure.getClass().getName();
    }

    @Override
    public void apply(Scene scene)
    {
        scene.addFigure(name, figure);
    }
}
