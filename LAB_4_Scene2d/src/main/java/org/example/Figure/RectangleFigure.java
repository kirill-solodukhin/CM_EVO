package org.example.Figure;

import org.example.Scene.SceneRectangle;

import java.awt.*;
import java.util.Arrays;

public class RectangleFigure implements Figure
{
    private final Point[] points = new Point[4];

    public RectangleFigure(Point p1, Point p2)
    {
        points[0] = p1;
        points[1] = p2;
        points[2] = new Point(p2.x, p1.y);
        points[3] = new Point(p1.x, p2.y);
    }

    @Override
    public SceneRectangle CalculateCircumscribingRectangle()
    {
        // todo
        return new SceneRectangle(points[0], points[1]);
    }

    @Override
    public void draw(Graphics2D g2d)
    {
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

        double centerX = (Arrays.stream(points).mapToDouble(p -> p.x).max().orElseThrow() +
                Arrays.stream(points).mapToDouble(p -> p.x).min().orElseThrow()) / 2;

        double centerY = (Arrays.stream(points).mapToDouble(p -> p.y).max().orElseThrow() +
                Arrays.stream(points).mapToDouble(p -> p.y).min().orElseThrow()) / 2;

        double cosAngle = Math.cos(angle);
        double sinAngle = Math.sin(angle);

        for (Point point : points)
        {
            newX = (int) ((point.getX() - centerX) * cosAngle
                    - (point.getY() - centerY) * sinAngle); // Math.abs();

            newY = (int) ((point.getX() - centerX) * sinAngle
                    + (point.getY() - centerY) * cosAngle); // Math.abs();

            point.setLocation(newX + centerX, newY + centerY);
        }
    }
}
