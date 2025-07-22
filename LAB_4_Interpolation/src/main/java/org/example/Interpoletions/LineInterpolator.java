package org.example.Interpoletions;

import java.util.Arrays;

public class LineInterpolator extends Interpolator
{
    public LineInterpolator(double[] xValues, double[] yValues)
    {
        super(xValues, yValues);
    }

    @Override
    public double CalculateValue(double x)
    {
        super.checkPoint(x);

        int maxIndex = Arrays.binarySearch(xValues, x);
        maxIndex = maxIndex  >= 0 ? maxIndex : -maxIndex - 1;

        double x0 = xValues[maxIndex - 1];
        double x1 = xValues[maxIndex];
        double y0 = yValues[maxIndex - 1];
        double y1 = yValues[maxIndex];

        return (y0 * (x1 - x) + y1 * (x - x0)) / (x1 - x0);
    }

}
