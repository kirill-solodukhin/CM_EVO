package org.example.Figure;

import org.example.Scene.SceneRectangle;

import java.awt.*;
import java.util.List;

public class PolygonFigure implements Figure
{
    private final List<Point> points;

    public PolygonFigure(List<Point> points)
    {
        this.points = points;
    }

    @Override
    public SceneRectangle CalculateCircumscribingRectangle()
    {
        int minX = points.stream().mapToInt(p -> (int) p.getX()).min().orElse(0);
        int minY = points.stream().mapToInt(p -> (int) p.getY()).min().orElse(0);
        int maxX = points.stream().mapToInt(p -> (int) p.getX()).max().orElse(0) + 1;
        int maxY = points.stream().mapToInt(p -> (int) p.getY()).max().orElse(0) + 1;

        return new SceneRectangle(new Point(minX, minY), new Point(maxX, maxY));
    }

    @Override
    public void draw(Graphics2D g2d)
    {
        for (int i = 1; i < points.size(); i++)
        {
            Point previousPoint = points.get(i - 1);
            Point thisPoint = points.get(i);

            g2d.drawLine(previousPoint.x, previousPoint.y, thisPoint.x, thisPoint.y);
        }

        Point firstPoint = points.getFirst();
        Point lastPoint = points.getLast();

        g2d.drawLine(lastPoint.x, lastPoint.y, firstPoint.x, firstPoint.y);
    }

    @Override
    public void move(Point vector)
    {
        for(Point p : points)
        {
            p.setLocation(
                    (int) p.getX() + vector.getX(),
                    (int) p.getY() + vector.getY()
            );
        }
    }

    @Override
    public void rotate(double angle)
    {

    }
}
