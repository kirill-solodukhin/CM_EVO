package org.example.Interpoletions;

public class LagrangeInterpolator extends Interpolator
{
    public LagrangeInterpolator(double[] xValues, double[] yValues)
    {
        super(xValues, yValues);
    }

    @Override
    public double CalculateValue(double x)
    {
        double result = 0;
        double polynome = 1;

        for(int i = 0; i < yValues.length; i++)
        {
            for (int j = 0; j < xValues.length; j++)
            {
                if(i == j)
                {
                    continue;
                }

                polynome *= (x - xValues[j]) / (xValues[i] - xValues[j]);
            }

            result += polynome * yValues[i];
            polynome = 1;
        }

        return result;
    }
}
