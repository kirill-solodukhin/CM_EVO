package org.example.Interpoletions;

import java.util.ArrayList;
import java.util.List;

public class NewtonInterpolator extends Interpolator
{
    public NewtonInterpolator(double[] xValues, double[] yValues)
    {
        super(xValues, yValues);
    }

    @Override
    public double CalculateValue(double x)
    {
        double result;
        double step = xValues[1] - xValues[0];
        double q = (x - xValues[0]) / step;

        List<List<Double>> dy = getFiniteDifferences();

        result = yValues[0] + q * dy.getFirst().getFirst();

        for (int i = 1; i < dy.getFirst().size() - 1; i++)
        {
            result += (q * (q - 1) * dy.get(i).getFirst()) / Factorial(i + 1);
        }

        return result;
    }

    private int Factorial(int n)
    {
        if (n == 1)
        {
            return 1;
        }

        return n * Factorial(n - 1);
    }

    private List<List<Double>> getFiniteDifferences()
    {
        List<List<Double>> dy = new ArrayList<>();

        dy.add(new ArrayList<>());
        for (int j = 1; j < yValues.length; j++)
        {
            dy.getFirst().add(yValues[j] - yValues[j - 1]);
        }

        double value;
        for (int i = 1; i < yValues.length; i++)
        {
            dy.add(new ArrayList<>());

            for(int j = 1; j < dy.get(i - 1).size(); j++)
            {
                value = (dy.get(i - 1).get(j)) - (dy.get(i - 1).get(j - 1));
                dy.get(i).add(value);
            }
        }

        return dy;
    }
}
