package org.example;

/// Для запуска проекта выполнить команду:
///
///  cd C:\Users\SoloduhinKYu\IdeaProjects\CM_EVO\LAB_7_Autocomplete
///
/// mvn compile exec:java "-Dexec.mainClass=org.example.Main"
///

public class Program
{
    private static LiveSearch liveSearch;
    private static HintedControl control;
    private static Thread searching;

    public static void main(String[] args)
    {
        control = new HintedControl();
        liveSearch = new LiveSearch(control);
        searching = new Thread(hintedSearch);

        searching.start();
        control.run();
    }

    private static final Runnable hintedSearch = () ->
    {
        while (true)
        {
            liveSearch.setHint();

            try
            {
                Thread.sleep(10);
            }
            catch (InterruptedException e)
            {
                throw new RuntimeException(e);
            }
        }
    };
}

/*

  Runnable task = () ->
        {
            do
            {
                if(gListener.keyCode == 15)
                {
                    System.out.println("Key Pressed: Tab");
                    break;
                }

                if(gListener.keyCode == 28)
                {
                    System.out.println("Key Pressed: Enter");
                }

                if(gListener.keyCode == 57424)
                {
                    System.out.println("Key Pressed: Arrow down");
                }

                try
                {
                    Thread.sleep(1);
                }
                catch (InterruptedException e)
                {
                    throw new RuntimeException(e);
                }
            }
            while (true);
        };

        Thread thread = new Thread(task);

        try
        {
            GlobalScreen.registerNativeHook();
        }
        catch (NativeHookException ex)
        {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());

            System.exit(1);
        }

        GlobalScreen.addNativeKeyListener(gListener);

        thread.start();
 */
