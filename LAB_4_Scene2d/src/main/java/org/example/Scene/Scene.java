package org.example.Scene;

import org.example.Exception.FigureNameAlreadyExistsException;
import org.example.Figure.Figure;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scene
{
    private final Map<String, Figure> figures = new HashMap<>();

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

    public SceneRectangle CalculateSceneSize()
    {
        List<SceneRectangle> allSceneRect = ListDrawableFigure().stream().map(Figure::CalculateFigureSize).toList();

        int maxWidth = allSceneRect.stream().mapToInt(SceneRectangle::width).max().orElse(0) + 1;
        int maxHeight = allSceneRect.stream().mapToInt(SceneRectangle::height).max().orElse(0)  +1;

        return new SceneRectangle(maxWidth, maxHeight);
    }
}
