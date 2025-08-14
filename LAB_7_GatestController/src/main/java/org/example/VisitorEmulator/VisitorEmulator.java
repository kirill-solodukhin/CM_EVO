package org.example.VisitorEmulator;

import org.example.GatestController.GatestManager;

import java.util.Random;


public class VisitorEmulator
{
    private final GatestManager manager;
    private volatile boolean stop = false;
    private final Thread[] threads = new Thread[4];

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
                Thread.sleep(random.nextInt(350, 1000));
                manager.requestOpen(4);
                Thread.sleep(random.nextInt(350, 1000));
                manager.requestClose(4);
            }
            catch (InterruptedException e)
            {
                break;
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
