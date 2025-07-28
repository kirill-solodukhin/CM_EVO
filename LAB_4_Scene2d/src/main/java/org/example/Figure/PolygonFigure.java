package org.example.Figure;

import org.example.Commands.Supportive.ReflectOrientation;
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
        int newX;
        int newY;

        Point center = getCenter();

        double centerX = center.x;
        double centerY = center.y;

        double cosAngle = Math.cos(Math.toRadians(angle));
        double sinAngle = Math.sin(Math.toRadians(angle));

        for (Point point : points)
        {
            newX = (int) (((point.getX() - centerX) * cosAngle
                    - (point.getY() - centerY) * sinAngle) + centerX);

            newY = (int) (((point.getX() - centerX) * sinAngle
                    + (point.getY() - centerY) * cosAngle) + centerY);

            point.setLocation(newX, newY);
        }

        checkOffset();
    }

    @Override
    public void reflect(ReflectOrientation orientation)
    {
        Point center = getCenter();

        if(orientation == ReflectOrientation.Horizontal)
        {
            for(Point point : points)
            {
                point.setLocation(
                        (point.x > center.x ?
                                (point.x - 2 * (point.x - center.x)) :
                                (point.x + 2 * (center.x - point.x))
                        ),
                        (point.y)
                );
            }

            return;
        }

        for(Point point : points)
        {
            point.setLocation(
                    (point.x),
                    (point.y > center.y ?
                            (point.y - 2 * (point.y - center.y)) :
                            (point.y + 2 * (center.y - point.y))
                    )
            );
        }
    }

    void checkOffset()
    {
        int minX = points.stream().mapToInt(p -> p.x).min().orElse(0);
        int minY = points.stream().mapToInt(p -> p.y).min().orElse(0);

        if(minX >= 0 && minY >= 0)
        {
            return;
        }

        for (Point point : points)
        {
            point.setLocation(point.x + (-minX), point.y + (-minY));
        }
    }

    Point getCenter()
    {
        double centerX = (points.stream().mapToDouble(p -> p.x).max().orElseThrow() +
                points.stream().mapToDouble(p -> p.x).min().orElseThrow()) / 2;

        double centerY = (points.stream().mapToDouble(p -> p.y).max().orElseThrow() +
                points.stream().mapToDouble(p -> p.y).min().orElseThrow()) / 2;

        return new Point((int)centerX, (int)centerY);
    }
}
