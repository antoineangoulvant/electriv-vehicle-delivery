package fr.uga.project.electricvehicledelivery.view;

import fr.uga.project.electricvehicledelivery.domain.InstanceSpecifications;

import javax.swing.*;
import java.awt.*;

public class InstanceSpecificationsPanel extends JPanel {
    private InstanceSpecifications instanceSpecifications;
    private JLabel distMax;
    private JLabel capaMax;
    private JLabel chargL;
    private JLabel chargM;
    private JLabel chargR;
    private JLabel debut;
    private JLabel fin;

    public void setInstanceSpecifications(InstanceSpecifications instanceSpecifications) {
        this.instanceSpecifications = instanceSpecifications;
        updateLabels();
    }

    public InstanceSpecificationsPanel(InstanceSpecifications instanceSpecifications) {
        this.instanceSpecifications = instanceSpecifications;
        setLayout(new GridLayout(7,2));

        distMax = new JLabel();
        capaMax = new JLabel();
        chargL = new JLabel();
        chargM = new JLabel("TEST");
        chargR = new JLabel();
        debut = new JLabel();
        fin = new JLabel();

        JLabel labelDistMax = new JLabel("Distance maximale : ");
        JLabel labelCapaMax = new JLabel("Capacité maximale : ");
        JLabel labelChargL = new JLabel("Temps charge lente : ");
        JLabel labelChargM = new JLabel("Temps charge moyenne : ");
        JLabel labelChargR = new JLabel("Temps charge rapide : ");
        JLabel labelDebut = new JLabel("Heure de début : ");
        JLabel labelFin = new JLabel("Heure de fin : ");

        add(labelDistMax);
        add(distMax);
        add(labelCapaMax);
        add(capaMax);
        add(labelChargL);
        add(chargL);
        add(labelChargM);
        add(chargM);
        add(labelChargR);
        add(chargR);
        add(labelDebut);
        add(debut);
        add(labelFin);
        add(fin);

        updateLabels();
    }

    public void updateLabels(){
        if(this.instanceSpecifications != null){
            distMax.setText(Integer.toString(instanceSpecifications.getMaxDist())+ " km");
            capaMax.setText(Integer.toString(instanceSpecifications.getCapacity()) + " sac(s)");
            chargL.setText(Integer.toString(instanceSpecifications.getChargeSlow()) + " min");
            chargM.setText(Integer.toString(instanceSpecifications.getChargeMedium()) + " min");
            chargR.setText(Integer.toString(instanceSpecifications.getChargeFast()) + " min");
            debut.setText(instanceSpecifications.getStartTime());
            fin.setText(instanceSpecifications.getEndTime());
        }
        this.repaint();
    }
}
