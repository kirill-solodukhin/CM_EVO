package org.example.Strings;

import java.util.Objects;
import java.util.concurrent.Callable;

public class SearchingProcess implements Callable<SimilarLine>
{
    private final String[] array;
    private final String word;

    public SearchingProcess(String[] array, String word)
    {
        this.array = array;
        this.word = word;
    }

    @Override
    public SimilarLine call()
    {
        SimilarLine best = new SimilarLine("", 0);

        for(String w : array)
        {
            SimilarLine current =
                    new SimilarLine(w, StringMetrics.longestCommonSubstringLength(w, word));

            if(current.isBetter(best))
            {
                best = current;
            }
        }

        if(Objects.equals(best.getLine(), ""))
        {
            return new SimilarLine(word, word.length());
        }

        return  best;
    }
}
