package org.example.Gatest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class App
{
    private Runnable windowListener;

    private final Fortress fortress = new Fortress();
    private final JTextArea jTextAreaVisitorsCount = new JTextArea("0");
    private final JTextArea[] jTextAreaVisitorsCountByGate = new JTextArea[5];

    public void addListener(Runnable handler)
    {
        //
        this.windowListener = handler;
    }

    public void createApp()
    {
        // Создаем окно
        JFrame frame = new JFrame("Gatest");

        // Установка операций закрытия
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);

        // Устанавливаем сетку для позиционирования
        panel.setLayout(new GridLayout(2, 1, 0 , 0));

        JPanel fort = fortress.createGates();
        JPanel info = getInfoPanel();

        panel.add(fort);
        panel.add(info);

        frame.add(panel);

        // Устанавливаем размеры
        frame.setSize(400, 600);

        // Добавляем слушатель обработчика окна
        frame.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                if(windowListener == null)
                {
                    return;
                }

                windowListener.run();
            }
        });

        // Центрируем на экране
        frame.setLocationRelativeTo(null);

        // Делаем окно видимым
        frame.setVisible(true);
    }

    public void setCountVisitorByGate(int gate, int count, int all)
    {
        if(gate > 4 || gate < 0)
        {
            return;
        }

        jTextAreaVisitorsCountByGate[gate].setText(count + "");
        jTextAreaVisitorsCount.setText(all + "");
    }

    private JPanel getInfoPanel()
    {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);

        // Сетка для всех таблиц
        panel.setLayout(new GridLayout(6, 1, 0 , 0));

        // Информация о всех посетителях
        JTextArea jTextAreaVisitorsText = new JTextArea("Visitors count: ");

        JPanel panel_visitorsCount = new JPanel();
        panel_visitorsCount.setBackground(Color.WHITE);
        panel_visitorsCount.setLayout(new GridLayout(1, 2, 20 , 0));
        panel_visitorsCount.add(jTextAreaVisitorsText);
        panel_visitorsCount.add(jTextAreaVisitorsCount);

        packingCounters();

        // Информация о посетитяеля через 1 гейт
        JTextArea jTextAreaVisitorsTextByGate1 = new JTextArea("Visitors count by gate 1: ");

        JPanel panel_visitorsCountByGate1 = new JPanel();
        panel_visitorsCountByGate1.setBackground(Color.WHITE);
        panel_visitorsCountByGate1.setLayout(new GridLayout(1, 2, 20 , 0));
        panel_visitorsCountByGate1.add(jTextAreaVisitorsTextByGate1);
        panel_visitorsCountByGate1.add(jTextAreaVisitorsCountByGate[0]);


        // Информация о посетитяеля через 2 гейт
        JTextArea jTextAreaVisitorsTextByGate2 = new JTextArea("Visitors count by gate 2: ");

        JPanel panel_visitorsCountByGate2 = new JPanel();
        panel_visitorsCountByGate2.setBackground(Color.WHITE);
        panel_visitorsCountByGate2.setLayout(new GridLayout(1, 2, 20 , 0));
        panel_visitorsCountByGate2.add(jTextAreaVisitorsTextByGate2);
        panel_visitorsCountByGate2.add(jTextAreaVisitorsCountByGate[1]);


        // Информация о посетитяеля через 3 гейт
        JTextArea jTextAreaVisitorsTextByGate3 = new JTextArea("Visitors count by gate 3: ");

        JPanel panel_visitorsCountByGate3 = new JPanel();
        panel_visitorsCountByGate3.setBackground(Color.WHITE);
        panel_visitorsCountByGate3.setLayout(new GridLayout(1, 2, 20 , 0));
        panel_visitorsCountByGate3.add(jTextAreaVisitorsTextByGate3);
        panel_visitorsCountByGate3.add(jTextAreaVisitorsCountByGate[2]);


        // Информация о посетитяеля через 4 гейт
        JTextArea jTextAreaVisitorsTextByGate4 = new JTextArea("Visitors count by gate 4: ");

        JPanel panel_visitorsCountByGate4 = new JPanel();
        panel_visitorsCountByGate4.setBackground(Color.WHITE);
        panel_visitorsCountByGate4.setLayout(new GridLayout(1, 2, 20 , 0));
        panel_visitorsCountByGate4.add(jTextAreaVisitorsTextByGate4);
        panel_visitorsCountByGate4.add(jTextAreaVisitorsCountByGate[3]);


        // Информация о посетитяеля через 5 гейт
        JTextArea jTextAreaVisitorsTextByGate5 = new JTextArea("Visitors count by gate 5: ");

        JPanel panel_visitorsCountByGate5 = new JPanel();
        panel_visitorsCountByGate5.setBackground(Color.WHITE);
        panel_visitorsCountByGate5.setLayout(new GridLayout(1, 2, 20 , 0));
        panel_visitorsCountByGate5.add(jTextAreaVisitorsTextByGate5);
        panel_visitorsCountByGate5.add(jTextAreaVisitorsCountByGate[4]);

        panel.add(panel_visitorsCount);
        panel.add(panel_visitorsCountByGate1);
        panel.add(panel_visitorsCountByGate2);
        panel.add(panel_visitorsCountByGate3);
        panel.add(panel_visitorsCountByGate4);
        panel.add(panel_visitorsCountByGate5);

        return panel;
    }

    private void packingCounters()
    {
        for (int i = 0; i < jTextAreaVisitorsCountByGate.length; i++)
        {
            jTextAreaVisitorsCountByGate[i] = new JTextArea("0");
        }
    }

    public void setGateStatus(int gateID, GateStaus staus)
    {
        fortress.setGateStatus(gateID, staus);
    }
}
