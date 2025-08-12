package org.example;


import org.jline.keymap.BindingReader;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.InfoCmp;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HintedControl
{
    private final Pattern pattern = Pattern.compile("\\S*$");

    private Terminal terminal;
    private BindingReader bindingReader;

    private StringBuilder text = new StringBuilder();
    private StringBuilder hint = new StringBuilder();

    private String lastWord;

    public HintedControl()
    {
        try
        {
            customizationTerminal();
            bindingReader = new BindingReader(terminal.reader());
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        processTyping();
    }

    public String getLastWord()
    {
        return lastWord;
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

    private void customizationTerminal() throws IOException
    {
        terminal = TerminalBuilder
                .builder()
                .system(true)
                .jna(true)
                .build();

        terminal.enterRawMode();

        terminal.puts(InfoCmp.Capability.clear_screen);
        terminal.flush();
    }

    public void processTyping()
    {
        while (true)
        {
            char character = (char) bindingReader.readCharacter();

            if(character == '\b') // backspace
            {
                backSpace();
                hint = getHint();

                display();
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
                display();

                continue;
            }

            text.append(character); // Строка
            hint = getHint();

            lastWord = detectedLastWord();
            display();
        }
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

        terminal.puts(InfoCmp.Capability.clear_screen);
    }

    private StringBuilder getHint()
    {
        return new StringBuilder(text)
                .append("a");
    }

    private void chooseHint()
    {
        text = new StringBuilder(hint.toString()); // set text
        hint = new StringBuilder(); // reset hint
    }

    private synchronized void display()
    {
        terminal.puts(InfoCmp.Capability.clear_screen);

        setText(); // Установка текста
        setHint(); // Установка подсказки

        terminal.puts(InfoCmp.Capability.cursor_up);
        terminal.flush();
    }

    private void setText()
    {
        System.out.print("\r" + text);
    }

    private void setHint()
    {
        System.out.print('\n');
        System.out.print("\r" + hint);
    }
}