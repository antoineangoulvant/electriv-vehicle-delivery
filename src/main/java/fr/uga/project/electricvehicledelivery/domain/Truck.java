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
    private double distance;
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
}
