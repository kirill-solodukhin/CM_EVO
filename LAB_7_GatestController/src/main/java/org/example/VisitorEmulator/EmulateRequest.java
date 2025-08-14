package org.example.VisitorEmulator;

import org.example.GatestController.GatestManager;

import java.util.Random;

public class EmulateRequest extends Thread
{
    private final Random random = new Random();
    private final GatestManager manager;
    private final int gate;

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
                Thread.sleep(random.nextInt(500, 3000));
                manager.requestOpen(gate);

                int visitorsCount = random.nextInt(5, 10);

                for (int i = 0; i < visitorsCount; i++)
                {
                    manager.registerVisitorEnter(gate);

                    Thread.sleep(random.nextInt(200, 2000));

                    manager.registerVisitorLeave(gate);
                }

                manager.requestClose(gate);
            }
            catch (InterruptedException e)
            {
                break;
            }
        }
    }
}
