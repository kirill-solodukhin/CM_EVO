package org.example;


import org.example.Exception.LargeCoefficientsException;
import org.example.Exception.ParseStrCoeffException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class Main
{
    public static void main(String[] args)
    {
        double[][] coefficients;

        String inputString = getText("Data/input.txt");

        if(inputString == null)
        {
            System.out.println("Входные данные отсутствуют");
            return;
        }

        try
        {
            coefficients = parseInput(inputString);
        }
        catch (LargeCoefficientsException | ParseStrCoeffException ex)
        {
            System.out.println(ex.getMessage());
            return;
        }

        Solver solver = new Solver(coefficients);
        shower(solver);
    }

    public static String getText(String path)
    {
        InputStream inputStream = Main.class
                .getClassLoader()
                .getResourceAsStream(path);

        if(inputStream == null)
        {
            System.out.println("Input stream is null, perhaps file is not exists");
            return null;
        }

        String line;
        StringBuilder sb = new StringBuilder();

        try(InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader))
        {
            while ((line = bufferedReader.readLine()) != null)
            {
                sb.append(line);
                sb.append("\n");
            }
        }
        catch (IOException ex)
        {
            System.out.println(ex.getMessage());
            return null;
        }

        return sb.toString();
    }

    public static double[][] parseInput(String inputString)

    {
        String[] lines = inputString.split("\n");
        String[] strCoefficients;

        int coll = Math.toIntExact(Arrays.stream(lines).count());

        double[][] result = new double[coll][3];
        double[] coefficients;

        int counter = 0;

        for(String str : lines)
        {
            strCoefficients = str.split(",");
            coefficients = parseSTRLine(strCoefficients);
            result[counter++] = coefficients;
        }

        return result;
    }

    public static double[] parseSTRLine(String[] strCoefficients) throws ParseStrCoeffException
    {
        double[] result = new double[3];
        int counter = 0;

        for (String el : strCoefficients)
        {
            try
            {
                result[counter] = Double.parseDouble(el.trim());
                counter++;
            }
            catch (NumberFormatException ex)
            {
                throw new ParseStrCoeffException("Error parse");
            }
            catch (IndexOutOfBoundsException ex)
            {
                throw new ParseStrCoeffException("A large number of coefficients");
            }
        }

        return result;
    }

    public static void shower(Solver solver)
    {
        List<Double> answer;
        StringBuilder sb = new StringBuilder("Answer ");
        int iterator = 1;

        while ((answer = solver.getAnswer()) != null)
        {
            sb.append(iterator++);
            sb.append(" = ");
            sb.append(answer);

            System.out.println(sb);
            sb = new StringBuilder("Answer ");
        }
    }
}