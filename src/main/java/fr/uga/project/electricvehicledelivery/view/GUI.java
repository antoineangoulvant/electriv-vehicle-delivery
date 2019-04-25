package fr.uga.project.electricvehicledelivery.view;

import fr.uga.project.electricvehicledelivery.Controller;
import fr.uga.project.electricvehicledelivery.heuristics.HeuristicsEnum;
import fr.uga.project.electricvehicledelivery.utils.Constants;

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

        final DefaultComboBoxModel assets = new DefaultComboBoxModel();
        Arrays.stream(Constants.Assets.values()).forEach(a ->
            assets.addElement(a.getInstance())
        );

        final JComboBox assetsList = new JComboBox(assets);

        addWindowListener(wl);
        setSize(800,600);
        setLocationRelativeTo(null);

        JPanel container = new JPanel();

        JPanel middlePanel = new JPanel();
        JLabel assetsListTitle = new JLabel("Liste des assets : ");
        middlePanel.add(assetsListTitle);
        middlePanel.add(assetsList);

        container.add(middlePanel);

        JPanel buttonsPanel = new JPanel();
        this.buttons = new ArrayList<>();
        Arrays.stream(HeuristicsEnum.values()).forEach(heuristic -> {
            JButton tempButton = new JButton(heuristic.toString());
            this.buttons.add(tempButton);
            buttonsPanel.add(tempButton);
            tempButton.addActionListener(this);
        });

        container.add(buttonsPanel);

        add(container);

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
