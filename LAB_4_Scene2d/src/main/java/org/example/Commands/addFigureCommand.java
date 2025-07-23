package org.example.Commands;

import org.example.Figure.Figure;
import org.example.Scene.Scene;

public class addFigureCommand implements Command
{
    private final String name;
    private final Figure figure;

    public addFigureCommand(String name, Figure figure)
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
