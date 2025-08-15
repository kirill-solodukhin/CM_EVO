package org.example.VisitorEmulator;

import org.example.GatestController.GatestManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EmulateRequest extends Thread
{
    private final Random random = new Random();
    private final GatestManager manager;
    private final int gate;

    private List<Thread> visitorsThreads;

    public EmulateRequest(GatestManager manager, int gate)
    {
        this.manager = manager;
        this.gate = gate;
    }

    @Override
    public void run()
    {
        while (true)
        {
            try
            {
                // Открытия
                Thread.sleep(random.nextInt(1500, 3000));
                manager.requestOpen(gate);

                // Проход
                int visitorsCount = random.nextInt(5, 10);
                visitorsThreads = new ArrayList<>(visitorsCount);

                for (int i = 0; i < visitorsCount; i++)
                {
                    visitorsThreads.add(
                            new Thread(() ->
                            {
                                try
                                {
                                    manager.registerVisitorEnter(gate);
                                    Thread.sleep(random.nextInt(750, 2000));
                                    manager.registerVisitorLeave(gate);
                                    Thread.sleep(random.nextInt(750, 2000));
                                }
                                catch (InterruptedException e)
                                {
                                    // System.out.println("Прерван поток посетителя через gate " + gate);
                                }
                            })
                    );

                    visitorsThreads.get(i).start();
                }

                for (Thread visitorsThread : visitorsThreads)
                {
                    visitorsThread.join();
                }

                Thread.sleep(random.nextInt(1500, 3000));
                manager.requestClose(gate);
            }
            catch (InterruptedException e)
            {
                for (Thread t : visitorsThreads)
                {
                    t.interrupt();
                }

                break; // Завершение потока
            }
        }
    }
}
