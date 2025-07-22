package org.example.Interpoletions;

import org.example.Exception.ArraysCountException;
import org.example.Exception.BadArgumentException;

public abstract class Interpolator
{
    protected final double[] xValues;
    protected final double[] yValues;

    public Interpolator(double[] xValues, double[] yValues)
    {
        checkValues(xValues, yValues);

        this.xValues = xValues;
        this.yValues = yValues;
    }

    public abstract double CalculateValue(double x);

    private void checkValues(double[] xValues, double[] yValues)
    {
        if(xValues.length != yValues.length)
        {
            throw new ArraysCountException("The number of values is not equal to the number of arguments");
        }

        if(xValues[0] >= xValues[yValues.length - 1])
        {
            throw new BadArgumentException("There cannot be identical points in the array of arguments," +
                    "or the end point must be larger than the start point");
        }
    }

    protected void checkPoint(double x)
    {
        if(x <= xValues[0] || x >= xValues[yValues.length - 1])
        {
            throw new BadArgumentException("The calculated value cannot exceed the known interval.");
        }
    }
}
