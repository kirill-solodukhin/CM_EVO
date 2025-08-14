package org.example.VisitorEmulator;

import java.util.Random;

public class VisitorEmulator
{
    private final Random random = new Random();

    private Runnable visitorEnter = () ->
    {
        int visitorsCount = random.nextInt(1, 10);
    };

    private Runnable visitorLeave = () ->
    {

    };
}
