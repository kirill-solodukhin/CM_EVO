package org.example;

import org.jline.terminal.Terminal;

import java.util.concurrent.Semaphore;

/// Для запуска проекта выполнить команду:
///
///  cd C:\Users\SoloduhinKYu\IdeaProjects\CM_EVO\LAB_7_Autocomplete
///
/// mvn compile exec:java "-Dexec.mainClass=org.example.Main"
///

public class Program
{
    private static LiveSearch liveSearch;       // Класс поиска подсказки
    private static HintedControl control;       // Класс контроля ввода
    private static Thread searching;            // Поток поиска
    private static Semaphore semaphore = new Semaphore(1);

    private static MyTerminal terminal;

    public static void main(String[] args)
    {
        terminal = new MyTerminal();            // Создание класса для работы с терминалом
        control = new HintedControl(terminal, semaphore);  // Создание класса обработки ввода
        liveSearch = new LiveSearch(control);   // Создание класса поиска подсказки
        searching = new Thread(hintedSearch);   // Создание потока для поиска подсказки

        searching.start();                      // Запуск поиска подсказки в потоке
        control.run();                          // Запуск обработк ввода
    }

    private static final Runnable hintedSearch = () ->
    {
        while (true)
        {
            try
            {
                semaphore.acquire();        // Запрашиваем разрешения для поиска или ждем
                liveSearch.setHint();       // Ищем
                                            // Освобождаем ресурсы, после обновления ввода
            }
            catch (InterruptedException e)
            {
                throw new RuntimeException(e);
            }
        }
    };
}