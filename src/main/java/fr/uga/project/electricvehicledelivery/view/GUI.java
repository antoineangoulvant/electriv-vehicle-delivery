package fr.uga.project.electricvehicledelivery.view;

import fr.uga.project.electricvehicledelivery.Controller;
import fr.uga.project.electricvehicledelivery.heuristics.HeuristicsEnum;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;

public class GUI extends JFrame implements ActionListener {
    private Controller controller;
    private ArrayList<JButton> buttons;

    public GUI(Controller controller){
        super("Problème de livraison des véhicules électriques");
        this.controller = controller;

        WindowListener wl = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        };

        addWindowListener(wl);
        setSize(800,600);
        setLocationRelativeTo(null);

        JPanel buttonsPanel = new JPanel();
        this.buttons = new ArrayList<>();
        Arrays.stream(HeuristicsEnum.values()).forEach(heuristic -> {
            JButton tempButton = new JButton(heuristic.toString());
            this.buttons.add(tempButton);
            buttonsPanel.add(tempButton);
            tempButton.addActionListener(this);
        });

        add(buttonsPanel);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        buttons.forEach(b -> {
            if(b.equals(e.getSource())){
                controller.launchHeuristic(b.getText());
            }
        });
    }
}
