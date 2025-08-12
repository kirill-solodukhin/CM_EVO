package org.example;

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
        hintedControl.setHintOutside(hint);
    }

    private String findBestSimilarAsync(String word)
    {


        return "dhbliszdbcjsbdcsa";
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
