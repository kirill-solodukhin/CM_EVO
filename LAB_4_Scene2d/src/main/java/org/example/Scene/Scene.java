package org.example.Scene;

import org.example.Commands.Supportive.ReflectOrientation;
import org.example.Exception.FigureNameAlreadyExistsException;
import org.example.Exception.FigureOrSceneIsNotExistsException;
import org.example.Figure.Figure;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scene
{
    private final Map<String, Figure> figures = new HashMap<>();
    private  final Map<String, List<Figure>> groups = new HashMap<>();

    public void addFigure(String name, Figure figure)
    {
        if(figures.containsKey(name))
        {
            throw new FigureNameAlreadyExistsException("A figure with that name has already been added to the scene.");
        }

        figures.put(name, figure);
    }

    public List<Figure> ListDrawableFigure()
    {
        return figures.values().stream().toList();
    }

    public SceneRectangle CalculateSceneCircumscribingRectangle()
    {
        List<SceneRectangle> allSceneRect = ListDrawableFigure().stream().map(Figure::CalculateCircumscribingRectangle).toList();

        int minX = allSceneRect.stream().mapToInt(el -> (int) el.leftTop().getX()).min().orElse(0);
        int maxX = allSceneRect.stream().mapToInt(el -> (int) el.rightBottom().getX()).max().orElse(0);
        int minY = allSceneRect.stream().mapToInt(el -> (int) el.leftTop().getY()).min().orElse(0);
        int maxY = allSceneRect.stream().mapToInt(el -> (int) el.rightBottom().getY()).max().orElse(0);

        Point leftTop = new Point(minX, minY);
        Point rightTop = new Point(maxX, maxY);

        return new SceneRectangle(leftTop, rightTop);
    }

    public void move(String name, Point vector)
    {
        if(figures.containsKey(name))
        {
            figures.get(name).move(vector);
            return;
        }

        if(groups.containsKey(name))
        {
            for (Figure figure: groups.get(name))
            {
                figure.move(vector);
            }

            return;
        }

        throw new FigureOrSceneIsNotExistsException("Object with name: " + name + " is not exists");
    }

    public void rotate(String name, double angle)
    {
        if(figures.containsKey(name))
        {
            figures.get(name).rotate(angle);
            return;
        }

        if(groups.containsKey(name))
        {
            for (Figure figure: groups.get(name))
            {
                figure.rotate(angle);
            }

            return;
        }

        throw new FigureOrSceneIsNotExistsException("Object with name: " + name + " is not exists");
    }

    public void reflect(String name, ReflectOrientation orientation)
    {
        if(figures.containsKey(name))
        {
            figures.get(name).reflect(orientation);
            return;
        }

        if(groups.containsKey(name))
        {
            for (Figure figure: groups.get(name))
            {
                figure.reflect(orientation);
            }

            return;
        }

        throw new FigureOrSceneIsNotExistsException("Object with name: " + name + " is not exists");
    }

    public void delete(String name)
    {
        if(figures.containsKey(name))
        {
            figures.remove(name);
            return;
        }

        if(groups.containsKey(name))
        {
            groups.remove(name);
            return;
        }

        throw new FigureOrSceneIsNotExistsException("Object with name: " + name + " is not exists");
    }

    public void copy(String copyFigureName, String name)
    {
        if(figures.containsKey(copyFigureName))
        {
            addFigure(name, figures.get(copyFigureName).copy());
            return;
        }

        throw new FigureOrSceneIsNotExistsException("Object with name: " + name + " is not exists");
    }

    public void setColor(String name, Color color)
    {
        if(figures.containsKey(name))
        {
            figures.get(name).setColor(color);
            return;
        }

        throw new FigureOrSceneIsNotExistsException("Object with name: " + name + " is not exists");
    }

    public void group(List<String> figuresName, String groupName)
    {
        List<Figure> figuresGroup = new ArrayList<>();

        for(String fName : figuresName)
        {
            if(!figures.containsKey(fName))
            {
                groups.remove(groupName);
                throw new FigureOrSceneIsNotExistsException("Object with name: " + fName + " is not exists");
            }

            figuresGroup.add(figures.get(fName));
        }

        groups.put(groupName, figuresGroup);
    }
}
