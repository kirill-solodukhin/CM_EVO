package org.example;

import java.io.File;
import java.nio.file.Paths;

public class Launcher
{
    // todo
    public static void main(String[] args)
    {
        try
        {
            String projectDir = Paths.get(System.getProperty("user.dir")).toString() + "\\LAB_8_MusicBrowser";
            File workDir = new File(projectDir);

            String command = String.format(
                    "cmd /c start \"MusicBrowser\" /D \"%s\" mvn compile exec:java -Dexec.mainClass=org.example.Program",
                    workDir.getAbsolutePath().replace("/", "\\")
            );

            ProcessBuilder pb =  new ProcessBuilder("cmd.exe", "/c", command)
                    .directory(new File(projectDir))
                    .inheritIO(); // Выыод ошибок в консоль

            pb.start();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
