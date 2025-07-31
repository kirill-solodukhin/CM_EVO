package org.example;

import org.example.Exceptions.ReadException;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main
{
    private static final SortedMap<String, String> usedColors = new TreeMap<>();
    private static SortedMap<String, String> allColors = new TreeMap<>();

    private static final ReadWriter readWriter = new ReadWriter();
    private static final MyColors myColors = new MyColors();

    private static String sourceText;
    private static String colorsText;

    public static void main(String[] args)
    {
        try
        {
            getTexts();
        }
        catch (ReadException ex)
        {
            System.out.println(ex.getMessage());
            return;
        }

        allColors = myColors.getColorsFromFile(colorsText);

        String replaceText = replaceText();

        writeOutputText(replaceText);
        writeUsedColor();
    }

    private static void argbReplace(String findString, Matcher matcher, StringBuilder sb)
    {
        findString = MyColors.rgbToHex(findString);
        String colorName;

        if(allColors.containsKey(findString))
        {
            colorName = allColors.get(findString);
            matcher.appendReplacement(sb, colorName);
            usedColors.put(findString, colorName);
        }
    }

    private static void hexReplace(String findString, Matcher matcher, StringBuilder sb)
    {
        if(findString.length() < 5)
        {
            findString = MyColors.hexExpanded(findString);
        }

        String colorName;

        if(allColors.containsKey(findString))
        {
            colorName = allColors.get(findString);
            matcher.appendReplacement(sb, colorName);
            usedColors.put(findString, colorName);
        }
    }

    private static void getTexts()
    {
        sourceText = readWriter.read("Data/source.txt");
        colorsText = readWriter.read("Data/colors.txt");
    }

    private static String replaceText()
    {
        Pattern pattern = Pattern.compile("(?<hexColor>#\\w{3,6})|(?<rgbColor>rgb\\(\\d+,\\s*\\d+,\\s*\\d+\\))");
        Matcher matcher = pattern.matcher(sourceText);
        StringBuilder sb = new StringBuilder();

        String findString;

        while (matcher.find())
        {
            if ((findString = matcher.group("hexColor")) != null)
            {
                hexReplace(findString, matcher, sb);
            }

            if ((findString = matcher.group("rgbColor")) != null)
            {
                argbReplace(findString, matcher, sb);
            }
        }

        matcher.appendTail(sb);

        return sb.toString();
    }

    public static void writeOutputText(String replaceText)
    {
        readWriter.write("LAB_3_ColorReplacement/OutputData/target.txt", "");
        readWriter.writeAppend("LAB_3_ColorReplacement/OutputData/target.txt", replaceText);
    }

    private static void writeUsedColor()
    {
        readWriter.write("LAB_3_ColorReplacement/OutputData/used_colors.txt", "");

        for(Map.Entry<String, String> el : usedColors.sequencedEntrySet())
        {
            Main.readWriter.writeAppend("LAB_3_ColorReplacement/OutputData/used_colors.txt",
                    el.getKey() + " = " + el.getValue() + '\n');
        }
    }
}