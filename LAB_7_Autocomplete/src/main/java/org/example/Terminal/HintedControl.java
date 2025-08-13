package org.example.Terminal;

import org.example.Program;

public class HintedControl
{
    private final MyTerminal terminal;
    private StringBuilder text = new StringBuilder();
    private StringBuilder hint = new StringBuilder();

    public HintedControl(MyTerminal terminal)
    {
        //
        this.terminal = terminal;
    }

    public void setHint(String value)
    {
        //
        hint = new StringBuilder(value);
    }

    public String getText()
    {
        //
        return text.toString();
    }

    public void run()
    {
        while (true)
        {
            char character = terminal.readCharacter();

            if(character == '\b') // backspace
            {
                backSpace();

                Program.startSearch();
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

            text.append(character);         // Строка

            Program.startSearch();
            terminal.display(text.toString(), hint.toString());
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

        terminal.clearTerminal();
    }

    private void chooseHint()
    {
        text = new StringBuilder(hint.toString()); // set text
        hint = new StringBuilder(); // reset hint
    }
}