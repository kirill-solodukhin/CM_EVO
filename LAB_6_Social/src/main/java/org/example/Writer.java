package org.example;

import org.example.Models.JSONModel;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


public class Writer
{
    public void write(String path, List<? extends JSONModel> list)
    {
        String text = getTextFromList(list);

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

    private String getTextFromList(List<? extends JSONModel> list)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");

        for(int i = 0; i < list.size() - 1; i++)
        {
            sb.append(list.get(i).entityToString()).append(",\n");
        }

        sb.append(list.getLast().entityToString());

        sb.append("\n]");

        return sb.toString();
    }
}
