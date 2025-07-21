package org.example;

import java.util.SortedMap;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyColors
{
    public static String rgbToHex(String col)
    {
        StringBuilder sb = new StringBuilder("#");
        Pattern pattern = Pattern.compile("\\d*[,)]");
        Matcher matcher = pattern.matcher(col);

        while (matcher.find())
        {
            sb.append(parseFind(matcher));
        }

        return sb.toString();
    }

    private static String parseFind(Matcher matcher)
    {
        int value = Integer.parseInt(matcher
                .group()
                .replace(",", "")
                .replace(")", ""));

        String sValue = Integer.toHexString(value);

        if(sValue.length() < 2)
        {
            sValue += sValue;
        }

        return sValue;
    }

    public static String hexExpanded(String oldColor)
    {
        return "#" + oldColor.charAt(1) +
                oldColor.charAt(1) +
                oldColor.charAt(2) +
                oldColor.charAt(2) +
                oldColor.charAt(3) +
                oldColor.charAt(3);
    }

    public SortedMap<String, String> getColorsFromFile(String txt)
    {
        SortedMap<String, String> allColors = new TreeMap<>();

        Pattern pattern = Pattern.compile(".*#.*\n");
        Matcher matcher = pattern.matcher(txt);
        String findingLine;

        while (matcher.find())
        {
            findingLine = matcher.group();
            addToMap(findingLine.split(" "), allColors);
        }

        return allColors;
    }

    private void addToMap(String[] color, SortedMap<String, String> allColors)
    {
        allColors.put(color[1].substring(0, 7), color[0]);
    }
}
