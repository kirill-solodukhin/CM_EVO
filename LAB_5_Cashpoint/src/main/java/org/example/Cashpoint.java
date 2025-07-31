package org.example;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Cashpoint
{
    // <nominal, count>
    private final Map<Integer, Integer> banknotes = new HashMap<>();
    private int[] granted = {1};

    private int total = 0;
    private int count = 0;

    public int getTotal()
    {
        return total;
    }

    public void addBanknote(int value)
    {
        if(banknotes.containsKey(value))
        {
            banknotes.put(value, banknotes.get(value) + 1);
        }
        else
        {
            banknotes.put(value, 1);
        }

        total += value;
        count++;
        
        int[] newGranted = new int[value + granted.length];
        System.arraycopy(granted, 0, newGranted, 0, granted.length);
        granted = newGranted;

        for(int i = total; i >= 0; i--)
        {
            if(granted[i] > 0)
            {
                granted[i + value] += granted[i];
            }
        }
    }

    public void addBanknote(int value, int count)
    {
        for(int i = 0; i < count; i++)
        {
            addBanknote(value);
        }
    }

    public void removeBanknote(int value)
    {
        if(!banknotes.containsKey(value))
        {
            return;
        }

        for(int i = 0; i < total; i++)
        {
            if(granted[i] <= 0)
            {
                continue;
            }

            if(granted[i + value] > 0)
            {
                granted[i + value] -= granted[i];
            }
        }

        granted = Arrays.copyOfRange(granted, 0, granted.length - value);

        banknotes.put(value, banknotes.get(value) - 1);
        total -= value;
        count--;

        if(banknotes.get(value) == 0)
        {
            banknotes.remove(value);
        }
    }

    public void removeBanknote(int value, int count)
    {
        for(int i = 0; i < count; i++)
        {
            removeBanknote(value);
        }
    }

    public boolean canGranted(int value)
    {
        if(value > total)
        {
            return false;
        }

        return granted[value] > 0;
    }
}