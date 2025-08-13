package org.example;

import org.example.Strings.LiveSearch;
import org.example.Terminal.HintedControl;
import org.example.Terminal.MyTerminal;

import java.util.concurrent.Semaphore;

/// Для запуска проекта выполнить команду:
///
///  cd C:\Users\SoloduhinKYu\IdeaProjects\CM_EVO\LAB_7_Autocomplete
///
/// mvn compile exec:java "-Dexec.mainClass=org.example.Main"
///

public class Program
{
    private static LiveSearch liveSearchClass;   // Класс поиска подсказки
    private static Thread searchingThread;       // Поток поиск

    public static void main(String[] args)
    {
        MyTerminal terminal = new MyTerminal();                             // Создание класса для работы с терминалом
        HintedControl control = new HintedControl(terminal);                // Создание класса обработки ввода
        liveSearchClass = new LiveSearch(control);                          // Создание класса поиска подсказки

        startSearch();                // Запуск поиска подсказки в потоке
        control.run();                          // Запуск обработк ввода
    }

    public static void startSearch()
    {
        if(searchingThread != null)
        {
            searchingThread.interrupt();                    // Прерываем предыдущий поиск
        }

        searchingThread = new Thread(runnableSearching);    // Создание потока для поиска подсказки
        searchingThread.start();
    }

    private static final Runnable runnableSearching = () ->
    {
        liveSearchClass.setHint();                          // Ищем
    };
}