package org.example;

import java.util.Random;

public class LiveSearch
{
    private final HintedControl hintedControl;

    public LiveSearch(HintedControl hintedControl)
    {
        this.hintedControl = hintedControl;
    }

    public void setHint()
    {
        String hint = findBestSimilarAsync(hintedControl.getLastWord());
        hintedControl.setHint(hint);
    }

    private String findBestSimilarAsync(String word)
    {
        Random random = new Random();
        return (char) random.nextInt(32, 127) + " " + (char) random.nextInt();
    }

    // todo remake this methode
    private String task(String[] words, String word)
    {
        String result;

        // todo added class BestSimilar

        Thread thread = new Thread(() ->
        {
            for (String w: words)
            {
                StringMetrics.longestCommonSubstringLength(w, word);
            }
        });

        thread.start();
        return " ";
    }
}
