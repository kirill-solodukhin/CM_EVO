package org.example.Figure;

import org.example.Commands.Supportive.ReflectOrientation;
import org.example.Scene.SceneRectangle;

import java.awt.*;

public interface Figure
{
    void draw(Graphics2D g2d);
    void move(Point vector);
    void rotate(double angle);
    void reflect(ReflectOrientation orientation);
    SceneRectangle CalculateCircumscribingRectangle();
}
