package org.example.GatestController;

import org.example.Gatest.App;
import org.example.Gatest.GateStaus;

import java.util.List;
import java.util.concurrent.Semaphore;

public class GatestManager
{
    private int visitorCounts = 0;
    private final int[] visitorByGates = new int [] { 0, 0, 0, 0, 0 };
    private final App app;

    private final Semaphore[] controlWall = new Semaphore[]
            {
                    new Semaphore(1), // left - 0 Right - 1 | ID - 0
                    new Semaphore(1), // left - 1 Right - 2 | ID - 1
                    new Semaphore(1), // left - 2 Right - 3 | ID - 2
                    new Semaphore(1), // left - 3 Right - 4 | ID - 3
                    new Semaphore(1)  // left - 4 Right - 0 | ID - 4
            };

    private final Semaphore semaphoreRequest = new Semaphore(2);

    public GatestManager(App app)
    {
        //
        this.app = app;
    }

    public void requestOpen(int gateID) throws InterruptedException
    {
        // Эти переменные разные от вызова к вызову,
        // за счет собственных стеков в каждом потоке
        int[] nWall = getNeighborsWall(gateID);
        int lWall = nWall[0];
        int rWall = nWall[1];

        app.setGateStatus(gateID, GateStaus.WAITING);
        semaphoreRequest.acquire(1);

        controlWall[lWall].acquire();
        controlWall[rWall].acquire();

        app.setGateStatus(gateID, GateStaus.OPEN);
    }

    public void requestClose(int gateID) throws InterruptedException
    {
        int[] nWall = getNeighborsWall(gateID);
        int lWall = nWall[0];
        int rWall = nWall[1];

        controlWall[lWall].release();
        controlWall[rWall].release();
        semaphoreRequest.release(1);

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

    private int[] getNeighborsWall(int gateID)
    {
        // left - right
        if(gateID == 4)
        {
            return new int[] { 3, gateID }; // l - 3 r - 4
        }

        if(gateID == 0) // l - 4 r - 0
        {
            return new int[] { 4, gateID };
        }

        return new int[] {gateID - 1, gateID};
    }
}
