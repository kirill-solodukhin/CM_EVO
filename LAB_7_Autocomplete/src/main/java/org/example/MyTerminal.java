package org.example;

import org.jline.keymap.BindingReader;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.InfoCmp;

import java.io.IOException;

public class MyTerminal
{
    private org.jline.terminal.Terminal terminal;
    private BindingReader bindingReader;
    private final  ProcessBuilder pb =  new ProcessBuilder("cmd.exe", "/c", "cls").inheritIO();

    public MyTerminal()
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
    }

    public char readCharacter()
    {
        //
        return (char) bindingReader.readCharacter();
    }

    public synchronized void display(String text, String hint)
    {
        terminal.puts(InfoCmp.Capability.clear_screen);

        setText(text); // Установка текста
        setHint(hint); // Установка подсказки

        terminal.puts(InfoCmp.Capability.cursor_up);
        terminal.flush();
    }

    public void clearTerminal()
    {
        try
        {
            pb.start();
        }
        catch (IOException e)
        {
          System.out.println(e.getMessage());
        }
    }

    private void customizationTerminal() throws IOException
    {
        terminal = TerminalBuilder      // Создае терминал
                .builder()
                .system(true)
                .jna(true)
                .build();

        terminal.enterRawMode();       // Включаем "Супер" режим

        terminal.puts(InfoCmp.Capability.clear_screen); // Очищаем терминал для ввода
        terminal.flush();                               // Применяем изменения
    }

    private void setText(String text)
    {
        //
        System.out.print("\r" + text);
    }

    private void setHint(String hint)
    {
        System.out.print('\n');
        System.out.print("\r" + hint);
    }
}
