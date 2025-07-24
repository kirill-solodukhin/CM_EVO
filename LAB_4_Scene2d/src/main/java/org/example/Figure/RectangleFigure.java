package org.example.Figure;

import org.example.Scene.SceneRectangle;

import java.awt.*;

public class RectangleFigure implements Figure
{
    private final Point p1; // Left top
    private final Point p2; // Right bottom
    private final Point p3; // Right top
    private final Point p4; // Left bottom

    public RectangleFigure(Point p1, Point p2)
    {
        this.p1 = p1;
        this.p2 = p2;

        p3 = new Point(p2.x, p1.y);
        p4 = new Point(p1.x, p2.y);
    }

    @Override
    public SceneRectangle CalculateCircumscribingRectangle()
    {
        return new SceneRectangle(p1, p2);
    }

    @Override
    public void draw(Graphics2D g2d)
    {
        g2d.drawLine(p1.x, p1.y, p3.x, p3.y); // top line
        g2d.drawLine(p1.x, p1.y, p4.x, p4.y); // left line
        g2d.drawLine(p4.x, p4.y, p2.x, p2.y); // bottom line
        g2d.drawLine(p3.x, p3.y, p2.x, p2.y); // right line
    }

    @Override
    public void move(Point vector)
    {
        p1.setLocation(p1.getX() + vector.x, p1.getY() + vector.y);
        p2.setLocation(p2.getX() + vector.x, p2.getY() + vector.y);
        p3.setLocation(p3.getX() + vector.x, p3.getY() + vector.y);
        p4.setLocation(p4.getX() + vector.x, p4.getY() + vector.y);
    }

    @Override
    public void rotate(double angle)
    {

    }
}
