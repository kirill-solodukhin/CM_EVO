package org.example.console;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ButtonsControl
{
    @Autowired
    private  CustomConsole console;

    @Autowired
    private Menu menu;

    private boolean isSpecialFlag = true;
    private char ch;

    public void run()
    {
        menu.loadingAlbums();
        menu.menu();

        while (true)
        {
            try
            {
                ch = console.readCharacter();

                if(isSpecialFlag)
                {
                    specialCharacterControl();
                    continue;
                }

                console.writeln(" " + ch + " ");
            }
            catch (RuntimeException _)
            {
                break;
            }
        }
    }

    public void goToMenu()
    {
        //
        isSpecialFlag = true;
    }

    private void specialCharacterControl()
    {
        if(ch == '\b')
        {
            menu.deleteEntity();
            return;
        }

        if(ch == '\r')
        {
            if(menu.expendedListIsOpen())
            {
                menu.closeExpendedList();
                return;
            }

            menu.openExpendedList();
            return;
        }

        if(ch == 'A')
        {
            menu.positionDown();
            return;
        }

        if(ch == 'B')
        {
            menu.positionUp();
            return;
        }

        if(ch == '\t')
        {
            return;
        }

        if(ch == 'a')
        {
            menu.addAlbum();
            return;
        }

        if(ch == 's')
        {
            menu.addSong();
        }

        if(ch == 'b')
        {
            throw new RuntimeException("back");
        }
    }
}
