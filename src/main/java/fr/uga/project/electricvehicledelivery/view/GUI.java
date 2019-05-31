package fr.uga.project.electricvehicledelivery.view;

import fr.uga.project.electricvehicledelivery.Controller;
import fr.uga.project.electricvehicledelivery.heuristics.HeuristicsEnum;
import fr.uga.project.electricvehicledelivery.utils.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Classe permettant de générer la fenêtre de l'application
 * @author Antoine Angoulvant - Andréas Dedieu Meille
 */
public class GUI extends JFrame implements ActionListener {
    private Controller controller;
    private ArrayList<JButton> buttons;
    private JComboBox<Constants.Assets> assetsList;
    private InstanceSpecificationsPanel instancePanel;

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
            assets.addElement(a)
        );


        this.assetsList = new JComboBox(assets);
        this.assetsList.addActionListener(this);

        addWindowListener(wl);
        setSize(800,400);
        setLocationRelativeTo(null);

        JPanel container = new JPanel();

        JPanel middlePanel = new JPanel();
        JLabel assetsListTitle = new JLabel("Liste des assets : ");
        middlePanel.add(assetsListTitle);
        middlePanel.add(this.assetsList);

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

        instancePanel = new InstanceSpecificationsPanel(controller.getInstanceSpecifications());
        container.add(instancePanel);

        container.add(new JLabel("Développé par Antoine Angoulvant et Andréas Dedieu Meille"));

        add(container);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       if(e.getSource().equals(this.assetsList)){
           JComboBox<Constants.Assets> combo = (JComboBox<Constants.Assets>) e.getSource();
           Constants.Assets selected = (Constants.Assets) combo.getSelectedItem();
           controller.loadInstance(selected.getInstance());
           instancePanel.setInstanceSpecifications(controller.getInstanceSpecifications());
       }
       buttons.forEach(b -> {
            if(b.equals(e.getSource())){
                controller.launchHeuristic(b.getText());
            }
       });
    }
}
