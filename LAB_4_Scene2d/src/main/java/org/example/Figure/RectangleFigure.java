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
    public void Draw(Graphics2D g2d)
    {
        g2d.drawLine(p1.x, p1.y, p3.x, p3.y); // top line
        g2d.drawLine(p1.x, p1.y, p4.x, p4.y); // left line
        g2d.drawLine(p4.x, p4.y, p2.x, p2.y); // bottom line
        g2d.drawLine(p3.x, p3.y, p2.x, p2.y); // right line
    }

    @Override
    public SceneRectangle CalculateFigureSize()
    {
        int width = p3.x - p1.x;
        int height = p4.y - p1.y;

        return new SceneRectangle(width, height);
    }
}
