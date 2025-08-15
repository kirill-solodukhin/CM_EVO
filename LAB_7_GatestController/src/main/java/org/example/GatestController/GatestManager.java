package org.example.GatestController;

import org.example.Gatest.App;
import org.example.Gatest.GateStaus;

import java.util.concurrent.Semaphore;

public class GatestManager
{
    private int visitorCounts = 0;
    private final int[] visitorByGates = new int [] { 0, 0, 0, 0, 0 };
    private final App app;

    private final Semaphore[] controlWall = new Semaphore[]
            {
                    new Semaphore(1), // 1 - 2
                    new Semaphore(1), // 2 - 3
                    new Semaphore(1), // 3 - 4
                    new Semaphore(1), // 4 - 5
                    new Semaphore(1)  // 5 - 1
            };

    public GatestManager(App app)
    {
        this.app = app;
    }

    public void requestOpen(int gateID)
    {
        app.setGateStatus(gateID, GateStaus.OPEN);
    }

    public void requestClose(int gateID)
    {
        //
        app.setGateStatus(gateID, GateStaus.CLOSED);
    }

    public synchronized void registerVisitorEnter(int gateID)
    {
        visitorCounts++;
        visitorByGates[gateID]++;
        app.setCountVisitorByGate(gateID, visitorByGates[gateID], visitorCounts);
    }

    public synchronized void registerVisitorLeave(int gateID)
    {
        visitorCounts--;
        visitorByGates[gateID]--;
        app.setCountVisitorByGate(gateID, visitorByGates[gateID], visitorCounts);
    }

    private int[] getNeighbors(int gateID)
    {
        if(gateID == 5)
        {
            return new int[] { 1, 4 };
        }

        return new int[] {};
    }
}
