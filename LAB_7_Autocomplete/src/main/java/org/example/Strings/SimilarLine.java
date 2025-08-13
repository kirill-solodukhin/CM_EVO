package org.example.Strings;

public class SimilarLine
{
    private final String line;
    private  final int score;

    public SimilarLine(String word, int score)
    {
        this.line = word;
        this.score = score;
    }

    public boolean isBetter(SimilarLine otherLine)
    {
        return (score > otherLine.score) ||
                (score == otherLine.score && line.length() < otherLine.line.length());
    }

    public String getLine()
    {
        return line;
    }
}
