package org.example.Gatest;

import javax.swing.*;
import java.awt.*;

public class Fortress
{
    private static Gate[] gates = new Gate[5];

    public JPanel createGates()
    {
        // Создаем задний фон и устанавливаем цвет
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);

        // Устанавливаем сетку для позиционирования
        panel.setLayout(new GridLayout(5, 5, 0 , 0));

        // Получаем ворота
        getGates();

        short counter = 0;
        for (int i = 0; i < 25; i++)
        {
            JPanel cell = new JPanel(new GridBagLayout());

            switch (i)
            {
                case 1, 3, 10, 14, 22:
                {
                    GridBagConstraints gbc = new GridBagConstraints();
                    gbc.anchor = GridBagConstraints.CENTER;

                    cell.add(gates[counter], gbc);
                    counter++;
                }

                default:
                {
                    panel.add(cell);
                }
            }
        }

        return panel;
    }

    public void setGateStatus(int gateID, GateStaus staus)
    {
        if(gateID > 4 || gateID < 0)
        {
            return;
        }

        gates[gateID].setColor(getColorByStatus(staus));
    }

    private Color getColorByStatus(GateStaus staus)
    {
        if(staus == GateStaus.OPEN)
        {
            return Color.GREEN;
        }

        if(staus == GateStaus.CLOSED)
        {
            return Color.DARK_GRAY;
        }

        if(staus == GateStaus.WAITING)
        {
            return Color.RED;
        }

        return Color.BLACK;
    }

    private static void getGates()
    {
        for (int i = 0; i < 5; i++)
        {
            gates[i] = new Gate();
        }
    }
}
