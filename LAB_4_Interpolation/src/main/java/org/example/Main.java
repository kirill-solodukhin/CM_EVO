package org.example;

import org.example.Interpoletions.*;

public class Main
{
    public static void main(String[] args)
    {
        double[] xValues = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        double[] yValues = {0, 1, 4, 9, 16, 25, 36, 49, 64, 81, 100};

        Interpolator[] interpolates =
                {
                        new LineInterpolator(xValues, yValues),
                        new StepInterpolator(xValues, yValues),
                        new NewtonInterpolator(xValues, yValues),
                        new LagrangeInterpolator(xValues, yValues)
                };

        for(Interpolator interpolator : interpolates)
        {
            System.out.println(interpolator.getClass().getSimpleName() + " = " + interpolator.CalculateValue(5.5));
        }
    }
}