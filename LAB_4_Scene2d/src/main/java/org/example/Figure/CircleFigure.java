package org.example.Figure;

import org.example.Scene.SceneRectangle;

import java.awt.*;

public class CircleFigure implements Figure
{
    private final Point center;
    private final int radius;

    public CircleFigure(Point center, int radius)
    {
        this.center = center;
        this.radius = radius;
    }

    @Override
    public void Draw(Graphics2D g2d)
    {
       int d = 2 * radius;
        g2d.drawArc(center.x - radius, center.y - radius, d, d, 0, 360 );
    }

    @Override
    public SceneRectangle CalculateFigureSize()
    {
        int d = 2 * radius;
        return new SceneRectangle(d, d);
    }
}
