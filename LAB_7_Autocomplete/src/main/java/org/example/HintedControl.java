package org.example;


import org.jline.keymap.BindingReader;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.InfoCmp;

import java.io.IOException;

public class HintedControl
{
    private Terminal terminal;
    private BindingReader bindingReader;

    private StringBuilder text = new StringBuilder();
    private StringBuilder hint = new StringBuilder();

    private int textLength;
    private int hintLength;

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

    private void customizationTerminal() throws IOException
    {
        terminal = TerminalBuilder
                .builder()
                .system(true)
                .jna(true)
                .build();

        terminal.enterRawMode();

        terminal.puts(InfoCmp.Capability.clear_screen);
        terminal.writer().println("Input: ");
        terminal.flush();
    }

    private void processTyping()
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

        System.out.println("\n");
        System.out.println("\n");
    }

    private StringBuilder getHint()
    {
        return new StringBuilder(text).append("a");
    }

    private void chooseHint()
    {
        text = new StringBuilder(hint.toString()); // set text
        int hintSize = hint.length();

        setText();

        // set and reset hint
        System.out.print('\n');
        clearRow(hintSize);
        System.out.print("\r" + hint);

        terminal.puts(InfoCmp.Capability.cursor_up);
        terminal.flush();

        hint = new StringBuilder(); // reset hint
    }

    private void display()
    {
        setText(); // Установка текста
        setHint(); // Установка подсказки

        terminal.puts(InfoCmp.Capability.cursor_up);
        terminal.flush();
    }

    private void clearRow(int count)
    {
        System.out.print('\r');

        for (int i = 0; i < count; i++)
        {
            System.out.print(" ");
        }

        System.out.print('\r');
    }

    private void setText()
    {
        clearRow(text.length() + 1);
        System.out.print("\r" + text);
    }

    private void setHint()
    {
        System.out.print('\n');
        clearRow(hint.length() + 1);

        System.out.print("\r" + hint);
    }
}