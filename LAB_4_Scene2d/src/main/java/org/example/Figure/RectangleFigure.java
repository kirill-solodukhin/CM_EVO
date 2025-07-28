package org.example.Figure;

import org.example.Commands.Supportive.ReflectOrientation;
import org.example.Scene.SceneRectangle;

import java.awt.*;
import java.util.Arrays;

public class RectangleFigure implements Figure
{
    private final Point[] points = new Point[4];
    private Color color;

    public RectangleFigure(Point p1, Point p2)
    {
        points[0] = p1;
        points[1] = p2;
        points[2] = new Point(p2.x, p1.y);
        points[3] = new Point(p1.x, p2.y);
    }

    private RectangleFigure(Point p1, Point p2, Color color)
    {
        points[0] = p1;
        points[1] = p2;
        points[2] = new Point(p2.x, p1.y);
        points[3] = new Point(p1.x, p2.y);

        this.color = color;
    }

    @Override
    public SceneRectangle CalculateCircumscribingRectangle()
    {
        int minX = Arrays.stream(points).mapToInt(p -> p.x).min().orElse(0);
        int minY = Arrays.stream(points).mapToInt(p -> p.y).min().orElse(0);

        int maxX = Arrays.stream(points).mapToInt(p -> p.x).max().orElse(0);
        int maxY = Arrays.stream(points).mapToInt(p -> p.y).max().orElse(0);

        return new SceneRectangle(new Point(minX, minY), new Point(maxX, maxY));
    }

    @Override
    public void draw(Graphics2D g2d)
    {
        g2d.setColor(color == null ? Color.BLUE : color);

        g2d.drawLine(points[0].x, points[0].y, points[2].x, points[2].y); // top line
        g2d.drawLine(points[0].x, points[0].y, points[3].x, points[3].y); // left line
        g2d.drawLine(points[3].x, points[3].y, points[1].x, points[1].y); // bottom line
        g2d.drawLine(points[2].x, points[2].y, points[1].x, points[1].y); // right line
    }

    @Override
    public void move(Point vector)
    {
        points[0].setLocation(points[0].getX() + vector.x, points[0].getY() + vector.y);
        points[1].setLocation(points[1].getX() + vector.x, points[1].getY() + vector.y);
        points[2].setLocation(points[2].getX() + vector.x, points[2].getY() + vector.y);
        points[3].setLocation(points[3].getX() + vector.x, points[3].getY() + vector.y);
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
            for (Point point : points)
            {
                point.setLocation(
                        (point.x > center.x ?
                                (point.x - 2 * (point.x - center.x)) :
                                (point.x + 2 * (center.x - point.x))
                        ),
                        point.y
                );
            }

            return;
        }

        for (Point point : points)
        {
            point.setLocation(
                    point.x,
                    (point.y > center.y ?
                            (point.y - 2 * (point.y - center.y)) :
                            (point.y + 2 * (center.y - point.y))
                    )
            );
        }
    }

    @Override
    public Figure copy()
    {
        return new RectangleFigure(
                new Point(points[0].x, points[0].y),
                new Point(points[1].x, points[1].y),
                (color == null ? null :  new Color(color.getRGB())));
    }

    @Override
    public void setColor(Color color)
    {
        this.color = color;
    }

    void checkOffset()
    {
        int minX = Arrays.stream(points).mapToInt(p -> p.x).min().orElse(0);
        int minY = Arrays.stream(points).mapToInt(p -> p.y).min().orElse(0);

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
        double centerX = (Arrays.stream(points).mapToDouble(p -> p.x).max().orElseThrow() +
                Arrays.stream(points).mapToDouble(p -> p.x).min().orElseThrow()) / 2;

        double centerY = (Arrays.stream(points).mapToDouble(p -> p.y).max().orElseThrow() +
                Arrays.stream(points).mapToDouble(p -> p.y).min().orElseThrow()) / 2;

        return new Point((int)centerX, (int)centerY);
    }
}
