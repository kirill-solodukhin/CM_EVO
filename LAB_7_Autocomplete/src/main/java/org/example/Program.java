package org.example;

import org.jline.reader.LineReader;


/// Для запуска проекта выполнить команду:
///
///  cd C:\Users\SoloduhinKYu\IdeaProjects\CM_EVO\LAB_7_Autocomplete
///
/// mvn compile exec:java "-Dexec.mainClass=org.example.Main"
///

import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

public class Program
{
    private static Terminal terminal;
    private static HintedControl control;

    public static void main(String[] args)
    {
        // System.out.println("Start - Stop");
        control = new HintedControl();
    }
    }

/*

      Terminal terminal = null;
        try
        {
            // Принудительные настройки для Jansi
            System.setProperty("jansi.passthrough", "true");
            System.setProperty("jansi.force", "true");
            System.setProperty("org.jline.terminal.force", "true");
            System.setProperty("org.jline.terminal.jansi.force", "true");

            // Инициализация терминала
            terminal = TerminalBuilder.builder()
                    .system(false)  // Не используем системный терминал
                    .type("jansi")  // Явно указываем тип
                    .dumb(false)    // Отключаем dumb-режим
                    .jna(true)      // Используем JNA
                    .build();

            // Инициализация LineReader
            LineReader reader = LineReaderBuilder.builder()
                    .terminal(terminal)
                    .appName("my-app")
                    .build();

            // Проверка инициализации Keys
            if (reader.getKeys() == null) {
                throw new IllegalStateException("LineReader keys not initialized");
            }

            // Регистрация обработчиков клавиш
            reader.getKeys().bind(new Reference("handle-tab"), "\t");
            reader.getKeys().bind(new Reference("handle-enter"), "\r");
            reader.getKeys().bind(new Reference("handle-down"), "\033[B");

            // Регистрация пользовательских виджетов
            reader.getWidgets().put("handle-tab", () -> handleTab(reader));
            reader.getWidgets().put("handle-enter", () -> handleEnter(reader));
            reader.getWidgets().put("handle-down", () -> handleDown(reader));

            System.out.println("Нажмите Tab, Enter или стрелку вниз (Ctrl+C для выхода)");

            while (true) {
                String line = reader.readLine("> ");
                System.out.println("Основной обработчик: " + line);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (terminal != null) {
                try {
                    terminal.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

 */

/*
 Terminal terminal = TerminalBuilder.builder().build();

            // Print some content
            terminal.writer().println("This is some content that will be cleared.");
            terminal.writer().println("Line 2");
            terminal.writer().println("Line 3");
            terminal.writer().println("Line 4");
            terminal.writer().println("Line 5");
            terminal.writer().flush();

            // Wait a moment
            TimeUnit.SECONDS.sleep(2);

            // Clear the screen
            terminal.puts(InfoCmp.Capability.clear_screen);
            terminal.flush();



            // Print new content
            terminal.writer().println("The screen has been cleared!");
            terminal.writer().println("This is new content.");
            terminal.writer().flush();

            terminal.close();




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

/*
        Runnable task_1 = () ->
        {
             for(int i = 0; i < 100; i++)
            {
                adder();

                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        Runnable task_2 = () ->
        {
             for(int i = 0; i < 100; i++)
            {
                adder();

                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        Runnable task_3 = () ->
        {
             for(int i = 0; i < 100; i++)
            {
                adder();

                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        Runnable task_4 = () ->
        {
             for(int i = 0; i < 100; i++)
            {
                adder();

                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        Runnable task_5 = () ->
        {
             for(int i = 0; i < 100; i++)
            {
                adder();

                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        Thread thread_1 = new Thread(task_1);
        Thread thread_2 = new Thread(task_1);
        Thread thread_3 = new Thread(task_1);
        Thread thread_4 = new Thread(task_1);
        Thread thread_5 = new Thread(task_1);

        thread_1.start();
        thread_2.start();
        thread_3.start();
        thread_4.start();
        thread_5.start();

        thread_1.join();
        thread_2.join();
        thread_3.join();
        thread_4.join();
        thread_5.join();

        System.out.println("Counter = " + counter);
 */