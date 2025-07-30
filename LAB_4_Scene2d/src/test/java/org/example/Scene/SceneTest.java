package org.example.Scene;

import org.example.Commands.Supportive.ReflectOrientation;
import org.example.Exception.FigureNameAlreadyExistsException;
import org.example.Exception.FigureOrSceneIsNotExistsException;
import org.example.Figure.CircleFigure;
import org.example.Figure.Figure;
import org.example.Figure.RectangleFigure;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.List;

public class SceneTest
{
    @Test
    public void addFigure_AddCorrectFiguresCommand_ShouldNotThrowException()
    {
        Scene scene = new Scene();
        scene.addFigure("Name", new CircleFigure(new Point(1,1), 10));
    }

    @Test
    public void addFigure_AddTwoIdenticalFigures_ShouldThrowException()
    {
        Scene scene = new Scene();
        Figure figure = new CircleFigure(new Point(1,1), 10);

        scene.addFigure("NAME", figure);
        FigureNameAlreadyExistsException exception = Assertions.assertThrows(FigureNameAlreadyExistsException.class,
                () -> scene.addFigure("NAME", figure));

        Assertions.assertEquals("A figure with name: NAME has already been added to the scene",
                exception.getMessage());
    }

    @Test
    public void listDrawableFigure_AddedThreeFigures_ShouldIncrementedSize()
    {
        Scene scene = new Scene();

        scene.addFigure("Figure_1", new CircleFigure(new Point(1,1), 10));
        scene.addFigure("Figure_2", new CircleFigure(new Point(1,1), 10));
        scene.addFigure("Figure_3", new CircleFigure(new Point(1,1), 10));

        int count = scene.listDrawableFigure().size();
        Assertions.assertEquals(3, count);
    }

    @Test
    public void listDrawableFigure_AddedThreeFiguresAfterException_ShouldIncrementedSize()
    {
        Scene scene = new Scene();

        scene.addFigure("Figure_1", new CircleFigure(new Point(1,1), 10));

        try
        {
            scene.addFigure("Figure_1", new CircleFigure(new Point(1,1), 10));
        }
        catch (Exception _)
        {
        }

        scene.addFigure("Figure_3", new CircleFigure(new Point(1,1), 10));

        int count = scene.listDrawableFigure().size();
        Assertions.assertEquals(2, count);
    }

    @Test
    public void listDrawableFigure_AddedAfterCopy_ShouldIncrementedSize()
    {
        Scene scene = new Scene();

        scene.addFigure("Figure_1", new CircleFigure(new Point(1,1), 10));
        scene.copy("Figure_1", "Figure_2");
        scene.copy("Figure_2", "Figure_3");

        int count = scene.listDrawableFigure().size();
        Assertions.assertEquals(3, count);
    }

    @Test
    public void listDrawableFigure_AddedAfterCopyGroup_ShouldIncrementedSize()
    {
        Scene scene = new Scene();

        scene.addFigure("Figure_1", new CircleFigure(new Point(1,1), 10));
        scene.copy("Figure_1", "Figure_2");
        scene.copy("Figure_2", "Figure_3");

        List<String> figures = List.of("Figure_1", "Figure_2", "Figure_3");
        scene.group(figures, "group");
        scene.copy("group", "new group");

        int count = scene.listDrawableFigure().size();
        Assertions.assertEquals(6, count);
    }

    @Test
    public void calculateSceneCircumscribingRectangle_AfterAddedFigure_ShouldCalculate()
    {
        Scene scene = new Scene();
        scene.addFigure("Figure_1", new CircleFigure(new Point(10,10), 10));

        SceneRectangle sceneRectangle = scene.calculateSceneCircumscribingRectangle();

        // x1
        Assertions.assertEquals(0, sceneRectangle.leftTop().x);
        // x2
        Assertions.assertEquals(20, sceneRectangle.rightBottom().x);
        // y1
        Assertions.assertEquals(0, sceneRectangle.leftTop().y);
        // y2
        Assertions.assertEquals(20, sceneRectangle.rightBottom().y);
    }

    @Test
    public void calculateSceneCircumscribingRectangle_AfterAddedListFigures_ShouldCalculate()
    {
        Scene scene = new Scene();
        CircleFigure circleFigure = new CircleFigure(new Point(10,10), 10);
        RectangleFigure rectangleFigure = new RectangleFigure(new Point(10,10), new Point(100,200));

        scene.addFigure("Figure_1", circleFigure);
        scene.addFigure("Figure_2", rectangleFigure);

        SceneRectangle sceneRectangle = scene.calculateSceneCircumscribingRectangle();

        // x1
        Assertions.assertEquals(0, sceneRectangle.leftTop().x);
        // x2
        Assertions.assertEquals(100, sceneRectangle.rightBottom().x);
        // y1
        Assertions.assertEquals(0, sceneRectangle.leftTop().y);
        // y2
        Assertions.assertEquals(200, sceneRectangle.rightBottom().y);
    }

    @Test
    public void calculateSceneCircumscribingRectangle_AfterMoveScene_ShouldCalculate()
    {
        Scene scene = new Scene();
        CircleFigure circleFigure = new CircleFigure(new Point(10,10), 10);
        RectangleFigure rectangleFigure = new RectangleFigure(new Point(10,10), new Point(100,200));

        scene.addFigure("Figure_1", circleFigure);
        scene.addFigure("Figure_2", rectangleFigure);
        scene.move("scene", new Point(100, 100));

        SceneRectangle sceneRectangle = scene.calculateSceneCircumscribingRectangle();

        // x1
        Assertions.assertEquals(100, sceneRectangle.leftTop().x);
        // x2
        Assertions.assertEquals(200, sceneRectangle.rightBottom().x);
        // y1
        Assertions.assertEquals(100, sceneRectangle.leftTop().y);
        // y2
        Assertions.assertEquals(300, sceneRectangle.rightBottom().y);
    }

    @Test
    public void move_figureIsExist_shouldThrowException()
    {
        Scene scene = new Scene();
        FigureOrSceneIsNotExistsException exception =
                Assertions.assertThrows(FigureOrSceneIsNotExistsException.class,
                        () -> scene.move("NAME", new Point()));

        Assertions.assertEquals("Object with name: NAME is not exists", exception.getMessage());
    }

    @Test
    public void rotate_figureIsExist_shouldThrowException()
    {
        Scene scene = new Scene();
        FigureOrSceneIsNotExistsException exception =
                Assertions.assertThrows(FigureOrSceneIsNotExistsException.class,
                        () -> scene.rotate("NAME", 90));

        Assertions.assertEquals("Object with name: NAME is not exists", exception.getMessage());
    }

    @Test
    public void reflect_figureIsExist_shouldThrowException()
    {
        Scene scene = new Scene();
        FigureOrSceneIsNotExistsException exception =
                Assertions.assertThrows(FigureOrSceneIsNotExistsException.class,
                        () -> scene.reflect("NAME", ReflectOrientation.Horizontal));

        Assertions.assertEquals("Object with name: NAME is not exists", exception.getMessage());
    }

    @Test
    public void delete_figureIsExist_shouldThrowException()
    {
        Scene scene = new Scene();
        FigureOrSceneIsNotExistsException exception =
                Assertions.assertThrows(FigureOrSceneIsNotExistsException.class,
                        () -> scene.delete("NAME"));

        Assertions.assertEquals("Object with name: NAME is not exists", exception.getMessage());
    }

    @Test
    public void copy_figureIsExist_shouldThrowException()
    {
        Scene scene = new Scene();
        FigureOrSceneIsNotExistsException exception =
                Assertions.assertThrows(FigureOrSceneIsNotExistsException.class,
                        () -> scene.copy("NAME", "NAME_2"));

        Assertions.assertEquals("Object with name: NAME is not exists", exception.getMessage());
    }

    @Test
    public void setColor_figureIsExist_shouldThrowException()
    {
        Scene scene = new Scene();
        FigureOrSceneIsNotExistsException exception =
                Assertions.assertThrows(FigureOrSceneIsNotExistsException.class,
                        () -> scene.setColor("NAME", new Color(1,1,1)));

        Assertions.assertEquals("Object with name: NAME is not exists", exception.getMessage());
    }
}