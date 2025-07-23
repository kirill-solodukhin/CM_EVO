package org.example.Figure;

import org.example.Scene.SceneRectangle;

import java.awt.*;

public interface Figure
{
    void Draw(Graphics2D g2d);
    SceneRectangle CalculateFigureSize();
}
