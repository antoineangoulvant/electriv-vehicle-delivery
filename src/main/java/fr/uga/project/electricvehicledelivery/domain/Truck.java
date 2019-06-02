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
}
