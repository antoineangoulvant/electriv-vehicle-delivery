package fr.uga.project.electricvehicledelivery.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant un camion de livraison
 * @author Antoine Angoulvant - Andréas Dedieu Meille
 */
@Data
public class Truck {
    /** Distance totale parcourue par le camion */
    private double distance;
    /** Durée totale de la journée de livraison */
    private int duration;
    /**
     * Liste contenant chaque point d'arrêt du camion
     * Un C représente un chargement de sac, un R un rechargement du camion
     */
    private List<String> deliveryPlanning;

    /**
     * Constructeur de la classe Truck qui initialise le planning
     */
    public Truck(){
        this.deliveryPlanning = new ArrayList<>();
    }

    /**
     * Méthode permettant d'ajouter des éléments dans le planning
     * @param toAdd élément à ajouter
     */
    public void addToPlanning(String toAdd){
        this.deliveryPlanning.add(toAdd);
    }

    /**
     * Méthode permettant d'augmenter le distance
     * @param toAdd distance à ajouter
     */
    public void addToDistance(double toAdd){
        this.distance += toAdd;
    }

    /**
     * Méthode permettant d'augmenter la durée
     * @param toAdd durée à ajouter
     */
    public void addToDuration(int toAdd){
        this.duration += toAdd;
    }

    public void updateDistanceAndDuration(Spots spots){
        Spot actualSpot = null;
        int duration = 0;
        double distance = 0;
        for(String id : this.getDeliveryPlanning()){
            if(actualSpot == null){
                if(id.equals("C") || id.equals("R")){
                    actualSpot = spots.getWarehouse();
                } else {
                    actualSpot = spots.getCustomers().stream().filter(c ->
                            c.getId() == Integer.parseInt(id)).findFirst().orElse(null);
                    duration += spots.getTimes()[spots.getWarehouse().getId()][actualSpot.getId()] +
                            ((Customer) actualSpot).getDeliveryDuration();
                    distance += spots.getDistances()[spots.getWarehouse().getId()][actualSpot.getId()];
                }
            } else {
                Spot tempSpot = null;
                int deliveryDuration = 0;
                if(id.equals("C") || id.equals("R")){
                    tempSpot = spots.getWarehouse();
                } else {
                    tempSpot = spots.getCustomers().stream().filter(c ->
                            c.getId() == Integer.parseInt(id)).findFirst().orElse(null);
                    deliveryDuration = ((Customer) tempSpot).getDeliveryDuration();
                }
                distance += spots.getDistances()[actualSpot.getId()][tempSpot.getId()];
                duration += spots.getTimes()[actualSpot.getId()][tempSpot.getId()] + deliveryDuration;
                actualSpot = tempSpot;
            }
        }
        this.setDuration(duration);
        this.setDistance(distance);
    }
}
