package org.example.Interpoletions;

import java.util.Arrays;

public class StepInterpolator extends Interpolator
{
    public StepInterpolator(double[] xValues, double[] yValues)
    {
        super(xValues, yValues);
    }

    @Override
    public double CalculateValue(double x)
    {
        super.checkPoint(x);
        int index;

        if((index = Arrays.binarySearch(xValues, x)) >= 0)
        {
            return yValues[index];
        }

        index = -index - 1;

        if((xValues[index] - x) > Math.abs((xValues[index - 1] - x)))
        {
            return yValues[index - 1];
        }

        return yValues[index];
    }
}
