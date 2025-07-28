package org.example.Figure;

import org.example.Commands.Supportive.ReflectOrientation;
import org.example.Scene.SceneRectangle;

import java.awt.*;

public interface Figure
{
    void draw(Graphics2D g2d);
    void move(Point vector);
    void reflect(ReflectOrientation orientation);
    void setColor(Color color);
    void rotate(double angle);
    Figure copy();
    SceneRectangle CalculateCircumscribingRectangle();
}
