package org.example;

import java.util.*;

public class Solver
{
    private final double[][] coefficients;
    private int indicator = 0;
    private final List<List<Double>> results = new ArrayList<>();

    public Solver(double[][] coefficients)
    {
        this.coefficients = coefficients;
        getResults();
    }

    private void getResults()
    {
        for (double[] el: coefficients)
        {
            results.add(Solve(el));
        }
    }

    private List<Double> Solve(double[] cof)
    {
        double eps = 0.000001;
        double d = Math.pow(cof[1], 2) - 4 * cof[0] * cof[2];

        if(cof[0] > -eps && cof[0] < eps) // a = 0
        {
            return List.of(
                    -cof[2] / cof[1]
            );
        }

        if(d < -eps) // d < 0
        {
            return Collections.emptyList();
        }

       if(d < eps && d > -eps) // d = 0
       {
           return List.of(
                   -cof[1] / (2 * cof[0])
           );
       }

        return Arrays.asList(
                (-cof[1] + Math.sqrt(cof[1] - 4 * cof[0] * cof[2])) / (2 * cof[0]),
                (-cof[1] - Math.sqrt(cof[1] - 4 * cof[0] * cof[2])) / (2 * cof[0])
        );
    }

    public List<Double> getAnswer()
    {
        try
        {
            return results.get(indicator++);
        }
        catch (Exception e)
        {
           return null;
        }
    }
}
