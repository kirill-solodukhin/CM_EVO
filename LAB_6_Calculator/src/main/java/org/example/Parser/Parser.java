package org.example.Parser;

import org.example.Exceptions.IncorrectParametersException;
import org.example.Exceptions.NotFoundOperationException;
import org.example.Operation;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser implements IParser
{
    private final Pattern pattern = Pattern.compile("(?<name>^\\s?[^ ]*\\s*)|(?<param>[+-]?[0-9]+\\.?[0-9]*\\s?)");
    private List<Double> param;
    private String name;

    @Override
    public Operation parse(String inputString)
    {
        param = new ArrayList<>();
        Matcher matcher = pattern.matcher(inputString);

        String findingLine;
        while (matcher.find())
        {
            if((findingLine = matcher.group("name")) != null)
            {
                name = findingLine.trim();
                continue;
            }

            if((findingLine = matcher.group("param")) != null)
            {
                param.add(Double.parseDouble(findingLine.trim()));
            }
        }

        throwIfBadCommand();

        return new Operation(name, param.stream().mapToDouble(el -> el).toArray());
    }

    private void throwIfBadCommand()
    {
        if(name == null || name.isEmpty())
        {
            throw new NotFoundOperationException(" ");
        }

        if(param.isEmpty())
        {
            throw new IncorrectParametersException(" ");
        }
    }
}
