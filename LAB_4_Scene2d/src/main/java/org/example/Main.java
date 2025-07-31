package org.example;

import org.example.CommandBuilders.CommandProducer;
import org.example.Commands.Command;
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
    public static void main(String[] args)
    {
        Scene scene = new Scene();
        List<String> commandLines = readCommandsFromFile("TestInput/hardInput.txt");

        CommandProducer commandProducer = new CommandProducer();
        for(String command : commandLines)
        {
            try
            {
                commandProducer.appendLine(command);

                if(commandProducer.isCommandReady())
                {
                    Command com = commandProducer.getCommand();
                    com.apply(scene);

                    System.out.println(com.friendlyResultMessage());
                }
            }
            catch (Exception ex)
            {
                System.out.println(ex.getMessage());
            }
        }

        try
        {
            DrawScene(scene);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    private static List<String> readCommandsFromFile(String path)
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
                if(line.matches("#-!.*"))
                {
                    break;
                }

                if(line.isEmpty() || line.trim().charAt(0) == '#')
                {
                    continue;
                }

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
        final String fileName = "LAB_4_Scene2d/output/shapes.png";

        SceneRectangle sr = scene.calculateSceneCircumscribingRectangle();

        int width = (int) (sr.rightBottom().getX())  + 1;
        int height = (int) (sr.rightBottom().getY()) + 1;

        // 1. Создаем изображение
        BufferedImage image = new BufferedImage(
                width, height,
                BufferedImage.TYPE_INT_RGB
        );

        // 2. Получаем графический контекст
        Graphics2D g2d = image.createGraphics();

        // 3. Настройка рендеринга (для сглаживания)
        g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        g2d.setColor(Color.BLUE);
        g2d.setStroke(new BasicStroke()); // Толщина линии

        // Отрисовка
        for (Figure f : scene.listDrawableFigure())
        {
            f.draw(g2d);
        }

        // 6. Освобождаем ресурсы
        g2d.dispose();

        // 7. Сохраняем в PNG
        File output = new File(fileName);
        ImageIO.write(image, "PNG", output);

        System.out.println("Изображение сохранено: " + output.getAbsolutePath());

    }
}