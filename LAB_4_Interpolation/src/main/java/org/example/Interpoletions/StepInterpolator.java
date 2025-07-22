package org.example.Interpoletions;

public class StepInterpolator extends Interpolator
{
    public StepInterpolator(double[] xValues, double[] yValues)
    {
        super(xValues, yValues);
    }

    @Override
    public double CalculateValue(double x)
    {
        return 0;
    }
}
