package Simulator;

import api.DirectedWeightedGraphAlgorithms;
import ex4_java_client.Client;
import ex4_java_client.StudentCode;
import imps.Agents;
import imps.Pokemons;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.awt.Frame.MAXIMIZED_BOTH;

public class FrameAlgo {
    public static int iter = 0;
    private DirectedWeightedGraphAlgorithms _algorithm;
    public GraphZone graphZone;
    public JFrame frame = new JFrame("GraphAlgorithms");
    private JPanel panel;
    private int mode;
    public static final int NONE = 0;
    private JLabel score;


    public FrameAlgo(DirectedWeightedGraphAlgorithms algorithm, Agents agents, Client client)
    {
        mode = NONE;
        _algorithm = algorithm;
        frame.setExtendedState(MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);

        panel = new JPanel();
        panel.setLayout(null);

        frame.add(panel);
        JButton stop = new JButton("Stop Running");
        stop.setBounds(20, 20, 150, 50);
        stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                synchronized (client)
                {
                    client.stop();
                    StudentCode.isRuning = false;
                }
            }
        });
        panel.add(stop);
        score = new JLabel("");
        score.setLocation(20, 150);
        score.setSize(100, 100);
        panel.add(score);

        graphZone = new GraphZone(_algorithm, agents, 120, 70, frame.getWidth() - 200, frame.getHeight()-200, panel);
        ImageIcon backGIM = new ImageIcon(this.getClass().getResource("/Simulator/pokenomframe.png"));
        backGIM = new ImageIcon(backGIM.getImage().getScaledInstance(frame.getWidth(), frame.getHeight(), Image.SCALE_SMOOTH));
        JLabel back = new JLabel(backGIM);
        back.setBounds(0,0, frame.getWidth(), frame.getHeight());
        back.setVisible(true);
        panel.add(back);
        //frame.pack();
        frame.repaint();


    }

    public void setAgentsAndPokes(Agents agents, Pokemons pokemons)
    {
        double totalS = 0;
        for (int i = 0; i < agents.size(); i++)
        {
            graphZone.setAgentsOnGraph(agents.getAgent(i));
            totalS += agents.getAgent(i).getValue();
        }
        score.setText("Score: " + totalS);
        graphZone.setPokemonsOnGraph(pokemons);
    }

}