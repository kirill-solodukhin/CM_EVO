package org.example.Figure;

import org.example.Commands.Supportive.ReflectOrientation;
import org.example.Scene.SceneRectangle;

import java.awt.*;

public class CircleFigure implements Figure
{
    private Point center;
    private final int radius;
    private Color color;

    public CircleFigure(Point center, int radius)
    {
        this.center = center;
        this.radius = radius;
    }

    private CircleFigure(Point center, int radius, Color color)
    {
        this.center = center;
        this.radius = radius;
        this.color = color;
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
        g2d.setColor(color == null ? Color.BLUE : color);

        int d = 2 * radius;
        g2d.drawArc(center.x - radius, center.y - radius, d, d, 0, 360 );
    }

    @Override
    public void move(Point vector)
    {
        center = new Point(
                (int) (center.getX() + vector.getX()),
                (int) (center.getY() + vector.getY()));
    }

    @Override
    public void rotate(double angle)
    {
    }

    @Override
    public void reflect(ReflectOrientation orientation)
    {
    }

    @Override
    public Figure copy()
    {
        return new CircleFigure(
                new Point(center.x, center.y), radius,
                (color == null ? null :  new Color(color.getRGB())));
    }

    @Override
    public void setColor(Color color)
    {
        this.color = color;
    }
}
