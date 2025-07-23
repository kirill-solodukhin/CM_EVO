package org.example;

import org.example.Exception.CommandFileNotFound;
import org.example.Figure.Figure;
import org.example.Scene.Scene;
import org.example.Scene.SceneRectangle;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main
{
    public static void main(String[] args) throws Exception
    {
        // DrawScene(new Scene());

        List<String> commands = readCommandFromFile("TestInput/Smile");
    }

    private static List<String> readCommandFromFile(String path)
    {
        InputStream inputStream = Main.class
                .getClassLoader()
                .getResourceAsStream(path);

        if(inputStream == null)
        {
            throw new CommandFileNotFound("Command file not found");
        }

        String line;
        List<String> commands = new ArrayList<>();

        try(InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader br = new BufferedReader(inputStreamReader))
        {
            while ((line = br.readLine()) != null)
            {
                commands.add(line);
            }
        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }

        return commands;
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