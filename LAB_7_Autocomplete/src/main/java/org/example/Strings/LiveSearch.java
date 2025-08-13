package org.example.Strings;

import org.example.Terminal.HintedControl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.FutureTask;

public class LiveSearch
{
    private static final String[] simpleWords = getLines("Data/words.txt");
    private static final String[] movieTitles  = getLines("Data/movies.txt");
    private static final String[] stageNames  = getLines("Data/stageNames.txt");

    private SimilarLine best = new SimilarLine("", 0);
    private final HintedControl hintedControl;

    public LiveSearch(HintedControl hintedControl)
    {
        //
        this.hintedControl = hintedControl;
    }

    public void setHint()
    {
        String hint = findBestSimilarAsync(hintedControl.getText());
        hintedControl.setHint(hint);
    }

    private String findBestSimilarAsync(String word)
    {
        SearchingProcess searchingProcess_1 = new SearchingProcess(simpleWords, word);  // Класс асинхронного поиска
        SearchingProcess searchingProcess_2 = new SearchingProcess(movieTitles, word);  // Класс асинхронного поиска
        SearchingProcess searchingProcess_3 = new SearchingProcess(stageNames, word);   // Класс асинхронного поиска

        FutureTask<SimilarLine> w_1 = new FutureTask<>(searchingProcess_1);   // Создали обьект из которого после заберем результат
        FutureTask<SimilarLine> w_2 = new FutureTask<>(searchingProcess_2);   // Создали обьект из которого после заберем результат

        Thread thread_1 = new Thread(w_1);
        Thread thread_2 = new Thread(w_2);

        try
        {
            thread_1.start();     // Запуск потока
            thread_2.start();     // Запуск потока
        }
        catch (Exception e)
        {
           thread_1.interrupt();
           thread_2.interrupt();
        }

        SimilarLine word_1;
        SimilarLine word_2;
        SimilarLine word_3;

        try
        {
            word_3 = searchingProcess_3.call();
            word_1 = w_1.get();
            word_2 = w_2.get();
        }
        catch (Exception e)
        {
            return best.getLine();
        }

        return getReturn(new SimilarLine[] { word_1, word_2, word_3 });
    }

   private static String[] getLines(String path)
   {
       InputStream inputStream = LiveSearch
               .class
               .getClassLoader()
               .getResourceAsStream(path);

       if(inputStream == null)
       {
           throw new RuntimeException("InputStream is null");
       }

       List<String> lines = new ArrayList<>();
       String line;

       try(InputStreamReader isr = new InputStreamReader(inputStream);
           BufferedReader br = new BufferedReader(isr))
       {
           while ((line = br.readLine()) != null)
           {
                lines.add(line);
           }

           return lines.toArray(String[]::new);
       }
       catch (IOException e)
       {
           throw new RuntimeException(e.getMessage());
       }
   }

   private String getReturn(SimilarLine[] array)
   {
       best = new SimilarLine("", 0);

       for(SimilarLine sl : array )
       {
           if(sl.isBetter(best))
           {
               best = sl;
           }
       }

       return best.getLine();
   }
}
