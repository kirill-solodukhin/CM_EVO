package org.example.Gatest;

import javax.swing.*;
import java.awt.*;

public class Gate extends JPanel
{
    private Color color;

    public Gate()
    {
        this.color = Color.GRAY;
    }

    public void setColor(Color color)
    {
        this.color = color;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        // Включаем сглаживание отрисовки
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Устанавливаем цвет и рисуем круг
        g2d.setColor(color);
        g2d.fillOval(0,0, getWidth(), getHeight());
    }
}
