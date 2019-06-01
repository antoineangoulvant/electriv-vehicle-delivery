package fr.uga.project.electricvehicledelivery.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Classe représentant une solution d'heuristique
 * @author Antoine Angoulvant - Andréas Dedieu Meille
 */
@NoArgsConstructor
@Data
public class Solution {
    private double totalDuration;
    private List<Truck> trucksList;
    private int nbConstraintDist;
    private int nbConstraintQuantity;
    private int nbConstraintDuraton;
    private int nbMissingVisits;
    private int nbMultipleVisits;

    /**
     * Méthode permettant de calculer la distance totale
     * @return distance totale
     */
    private Double getTotalDistance(){
        return trucksList.stream().mapToDouble(Truck::getDistance).sum();
    }

    public double evaluate(){
        return this.getTotalDistance() +
                totalDuration +
                ((trucksList.size() - 1) * 500) +
                nbConstraintDist * 50000 +
                nbConstraintQuantity * 10000 +
                nbConstraintDuraton * 1000 +
                (nbMissingVisits+nbMultipleVisits) * 100000;
    }
}
