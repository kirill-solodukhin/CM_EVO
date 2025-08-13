package org.example;


import java.util.concurrent.Semaphore;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HintedControl
{
    private final MyTerminal terminal;

    private final Pattern pattern = Pattern.compile("\\S*$");
    private final Semaphore semaphore;
    private StringBuilder text = new StringBuilder();
    private StringBuilder hint = new StringBuilder();
    private String lastWord = "";

    public HintedControl(MyTerminal terminal, Semaphore semaphore)
    {
        this.terminal = terminal;
        this.semaphore = semaphore;
    }

    public void setHint(String value)
    {
        //
        hint = new StringBuilder(value);
    }

    public String getLastWord()
    {
        //
        return lastWord;
    }

    public void run()
    {
        while (true)
        {
            char character = terminal.readCharacter();
            semaphore.release();

            if(character == '\b') // backspace
            {
                backSpace();

                terminal.display(text.toString(), hint.toString());
                continue;
            }

            if(character == '\r') // enter
            {
                enter();
                continue;
            }

            if(character == '\t') // tab
            {
                chooseHint();
                terminal.display(text.toString(), hint.toString());

                continue;
            }

            text.append(character); // Строка

            lastWord = detectedLastWord();
            terminal.display(text.toString(), hint.toString());
        }
    }

    private String detectedLastWord()
    {
        Matcher matcher = pattern.matcher(text);

        if(matcher.find())
        {
            return matcher.group();
        }

        return "";
    }

    private void backSpace()
    {
        if(text.isEmpty())
        {
            return;
        }

        text.deleteCharAt(text.length() - 1);
    }

    private void enter()
    {
        text = new StringBuilder();
        hint = new StringBuilder();

        terminal.clearTerminal();
    }

    private void chooseHint()
    {
        text = new StringBuilder(hint.toString()); // set text
        hint = new StringBuilder(); // reset hint
    }
}