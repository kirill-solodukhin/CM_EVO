package org.example.console;

import org.jline.keymap.BindingReader;
import org.jline.reader.EndOfFileException;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.InfoCmp;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomConsole
{
    private Terminal terminal;
    private BindingReader bindingReader;
    private LineReader lineReader;

    public CustomConsole()
    {
        try
        {
            createTerminal();
            createBindingReader();
            createLineReader();
        }
        catch (IOException _)
        {

        }
    }

    private void createTerminal() throws IOException
    {
        this.terminal = TerminalBuilder                 // Создаем терминал
                .builder()
                .system(true)
                .build();

        terminal.enterRawMode();                        // Включаем "Супер" режим

        terminal.puts(InfoCmp.Capability.clear_screen); // Очищаем терминал для ввода
        terminal.flush();                               // Применяем изменения
    }

    private void createBindingReader()
    {
        //
        bindingReader = new BindingReader(terminal.reader());
    }

    private void createLineReader()
    {
        lineReader= LineReaderBuilder
                .builder().terminal(terminal).build();
    }

    public void clear()
    {
        //
        terminal.puts(InfoCmp.Capability.clear_screen);
    }

    public void write(Object obj)
    {
        //
        terminal.writer().print(obj);
    }

    public void writeln(Object obj)
    {
        //
        terminal.writer().println(obj);
    }

    public char readCharacter()
    {
        //
        return (char) bindingReader.readCharacter();
    }

    public String read(String hint)
    {
        try
        {
            return lineReader.readLine(hint);
        }
        catch (EndOfFileException e)
        {
            throw new RuntimeException(e);
        }
    }
}
