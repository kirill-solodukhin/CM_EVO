package org.example.GatestController;

import org.example.Gatest.App;
import org.example.Gatest.GateStaus;

public class GatestManager
{
    private int visitorCounts = 0;
    private final int[] visitorByGates = new int [] { 0, 0, 0, 0, 0 };
    private final App app;

    public GatestManager(App app)
    {
        this.app = app;
    }

    public void requestOpen(int gateID)
    {
        //
        app.setGateStatus(gateID, GateStaus.OPEN);
    }

    public void requestClose(int gateID)
    {
        //
        app.setGateStatus(gateID, GateStaus.CLOSED);
    }

    public void registerVisitorEnter(int gateID)
    {
        visitorCounts++;
        visitorByGates[gateID]++;
        app.setCountVisitorByGate(gateID, visitorByGates[gateID], visitorCounts);
    }

    public void registerVisitorLeave(int gateID)
    {
        visitorCounts--;
        visitorByGates[gateID]--;
        app.setCountVisitorByGate(gateID, visitorByGates[gateID], visitorCounts);
    }
}
