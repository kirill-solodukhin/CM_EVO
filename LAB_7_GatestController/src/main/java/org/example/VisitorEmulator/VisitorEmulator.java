package org.example.VisitorEmulator;

import org.example.GatestController.GatestManager;

import java.util.List;
import java.util.Random;


public class VisitorEmulator
{
    private final GatestManager manager;
    private volatile boolean stop = false;
    private final Thread[] threads = new Thread[4];
    private List<Thread> visitorsThreads;

    public VisitorEmulator(GatestManager manager)
    {
        //
        this.manager = manager;
    }

    public void run()
    {
        for (int i = 0; i < 4; i++)
        {
            threads[i] = new Thread(new EmulateRequest(manager, i));
            threads[i].start();
        }

        Random random = new Random();
        while (!stop)
        {
            try
            {
                // Открытия
                Thread.sleep(random.nextInt(1500, 3000));
                manager.requestOpen(4);

                // Проход
                int visitorsCount = random.nextInt(5, 10);

                for (int i = 0; i < visitorsCount; i++)
                {
                    new Thread(() ->
                    {
                        try
                        {
                            manager.registerVisitorEnter(4);
                            Thread.sleep(random.nextInt(750, 2000));
                            manager.registerVisitorLeave(4);
                            Thread.sleep(random.nextInt(750, 2000));
                        }
                        catch (InterruptedException e)
                        {
                            throw new RuntimeException(e);
                        }
                    }).start();
                }

                for (Thread visitorsThread : visitorsThreads)
                {
                    visitorsThread.join();
                }

                Thread.sleep(random.nextInt(1500, 3000));
                manager.requestClose(4);
            }
            catch (InterruptedException e)
            {
                break; // Завершение потока
            }
        }
    }

    public void stopped()
    {
        stop = true;

        for (int i = 0; i < 4; i++)
        {
            threads[i].interrupt();
        }
    }
}
