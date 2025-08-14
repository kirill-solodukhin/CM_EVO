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
                Thread.sleep(random.nextInt(100, 500));
                manager.requestOpen(gate);
                Thread.sleep(random.nextInt(100, 500));
                manager.requestClose(gate);
            }
            catch (InterruptedException e)
            {
                break;
            }
        }
    }
}
