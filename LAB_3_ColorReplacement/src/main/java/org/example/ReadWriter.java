package org.example;

import org.example.Exceptions.ReadException;

import java.io.*;

public class ReadWriter
{
    public String read(String path)
    {
        InputStream inputStream = ReadWriter
                .class
                .getClassLoader()
                .getResourceAsStream(path);

        if(inputStream == null)
        {
            throw new ReadException("InputStream is null");
        }

        StringBuilder sb = new StringBuilder();
        String line;

        try(InputStreamReader isr = new InputStreamReader(inputStream);
            BufferedReader br = new BufferedReader(isr))
        {
            while ((line = br.readLine()) != null)
            {
                sb.append(line);
                sb.append("\n");
            }

            return sb.toString();
        }
        catch (IOException e)
        {
            throw new ReadException(e.getMessage());
        }
    }

    public void writeAppend(String path, String text)
    {
        try(FileWriter fileWriter = new FileWriter(path, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter))
        {
            bufferedWriter.write(text);
        }
        catch (IOException ex)
        {
            System.out.println(ex.getMessage());
        }

    }
    public void write(String path, String text)
    {
        try(FileWriter fileWriter = new FileWriter(path, false);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter))
        {
            bufferedWriter.write(text);
        }
        catch (IOException ex)
        {
            System.out.println(ex.getMessage());
        }

    }

}
