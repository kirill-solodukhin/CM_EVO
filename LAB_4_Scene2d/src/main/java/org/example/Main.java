package org.example;

import org.example.Figure.Figure;
import org.example.Scene.Scene;
import org.example.Scene.SceneRectangle;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main
{
    public static void main(String[] args) throws Exception
    {
//        // 1. Создаем изображение (разрешение 500x500)
//        BufferedImage image = new BufferedImage(
//                500, 500,
//                BufferedImage.TYPE_INT_RGB
//        );
//
//        // 2. Получаем графический контекст
//        Graphics2D g2d = image.createGraphics();
//
//        // 3. Настройка рендеринга (для сглаживания)
//        g2d.setRenderingHint(
//                RenderingHints.KEY_ANTIALIASING,
//                RenderingHints.VALUE_ANTIALIAS_ON
//        );
//
//        // 4. Заливка фона
//        g2d.setColor(Color.WHITE);
//        g2d.fillRect(0, 0, 500, 500);
//
//        // 5. Рисуем фигуры:
//        // Красный прямоугольник
//        g2d.setColor(Color.RED);
//        g2d.fillRect(50, 50, 150, 100);
//
//        // Синий круг
//        g2d.setColor(Color.BLUE);
//        g2d.fillOval(250, 50, 150, 150);
//
//        // Зеленая линия
//        g2d.setColor(Color.GREEN);
//        g2d.setStroke(new BasicStroke(5)); // Толщина линии
//        g2d.drawLine(50, 300, 450, 300);
//
//        // Желтый треугольник (многоугольник)
//        int[] xPoints = {200, 300, 250};
//        int[] yPoints = {400, 400, 300};
//        g2d.setColor(Color.YELLOW);
//        g2d.fillPolygon(xPoints, yPoints, 3);
//
//        // Оранжевая дуга
//        g2d.setColor(new Color(255, 165, 0));
//        g2d.drawArc(350, 250, 100, 100, 45, 270);
//
//        // 6. Освобождаем ресурсы
//        g2d.dispose();
//
//        // 7. Сохраняем в PNG
//        File output = new File("LAB_4_Scene2d/shapes.png");
//        ImageIO.write(image, "PNG", output);
//
//        System.out.println("Изображение сохранено: " + output.getAbsolutePath());

        DrawScene(new Scene());
    }

    private static  void DrawScene(Scene scene) throws IOException
    {
        final String fileName = "LAB_4_Scene2d/shapes.png";

        SceneRectangle sr = scene.CalculateSceneSize();

        // 1. Создаем изображение
        BufferedImage image = new BufferedImage(
                sr.width(), sr.height(),
                BufferedImage.TYPE_INT_RGB
        );

        // 2. Получаем графический контекст
        Graphics2D g2d = image.createGraphics();

        // 3. Настройка рендеринга (для сглаживания)
        g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        // Отрисовка

        for (Figure f : scene.ListDrawableFigure())
        {
            f.Draw(g2d);
        }

        g2d.setColor(Color.BLUE);
        g2d.setStroke(new BasicStroke()); // Толщина линии
        g2d.drawLine(50, 300, 450, 300);

        // 6. Освобождаем ресурсы
        g2d.dispose();

        // 7. Сохраняем в PNG
        File output = new File("LAB_4_Scene2d/shapes.png");
        ImageIO.write(image, "PNG", output);

        System.out.println("Изображение сохранено: " + output.getAbsolutePath());

    }

}