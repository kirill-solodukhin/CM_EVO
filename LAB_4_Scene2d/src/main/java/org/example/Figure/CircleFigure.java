package org.example.Figure;

import org.example.Scene.SceneRectangle;

import java.awt.*;

public class CircleFigure implements Figure
{
    private Point center;
    private final int radius;

    public CircleFigure(Point center, int radius)
    {
        this.center = center;
        this.radius = radius;
    }

    @Override
    public SceneRectangle CalculateCircumscribingRectangle()
    {
        Point leftTop = new Point(center.x - radius, center.y - radius);
        Point rightTop = new Point(center.x + radius, center.y + radius);

        return new SceneRectangle(leftTop, rightTop);
    }

    @Override
    public void draw(Graphics2D g2d)
    {
        int d = 2 * radius;
        g2d.drawArc(center.x - radius, center.y - radius, d, d, 0, 360 );
    }

    @Override
    public void move(Point vector)
    {
        center = new Point(
                (int) (center.getX() + vector.getX()),
                (int) (center.getX() + vector.getY()));
    }

    @Override
    public void rotate(double angle)
    {

    }
}
