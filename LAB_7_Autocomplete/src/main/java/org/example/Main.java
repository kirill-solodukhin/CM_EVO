package org.example;

import java.io.File;
import java.nio.file.Paths;

public class Main
{
    public static void main(String[] args)
    {
        try
        {

            String projectDir = Paths.get(System.getProperty("user.dir")).toString() + "\\LAB_7_Autocomplete";
            File workDir = new File(projectDir);

            System.out.println(projectDir);

            String command = String.format(
                    "cmd /c start \"Autocomplete\" /D \"%s\" mvn compile exec:java -Dexec.mainClass=org.example.Program",
                    workDir.getAbsolutePath().replace("/", "\\")
            );

            System.out.println(command);

            ProcessBuilder pb=  new ProcessBuilder("cmd.exe", "/c", command)
                    .directory(new File(projectDir))
                    .inheritIO(); // Выыод ошибок в консоль

            System.out.println(pb.command());
            pb.start();

            System.out.println("Основное приложение продолжает работу. Терминал запущен в отдельном окне.");

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
