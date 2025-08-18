package org.example;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ReadWriter
{
    public static String readFromFile(String uri) throws IOException
    {
        Path path = Paths.get(uri);

        String line;
        StringBuilder sb = new StringBuilder();

        try(InputStream inputStream = Files.newInputStream(path);
            InputStreamReader ipr = new InputStreamReader(inputStream);
            BufferedReader br = new BufferedReader(ipr))
        {
            while ((line = br.readLine()) != null)
            {
                sb.append(line);
            }
        }

        return sb.toString();
    }

    public static void write(String path, String text) throws IOException
    {
        try(FileWriter fileWriter = new FileWriter(path, false);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter))
        {
            bufferedWriter.write(text);
        }
    }
}
