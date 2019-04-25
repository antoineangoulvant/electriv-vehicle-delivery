package fr.uga.project.electricvehicledelivery.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class Solution {
    private double totalDistance;
    private double totalDuration;
    private List<Truck> trucksList;
    private int nbConstraintDist;
    private int nbConstraintQuantity;
    private int nbConstraintDuraton;
    private int nbMissingVisits;
    private int nbMultipleVisits;

    public double evaluate(){
        return totalDistance +
                totalDuration +
                ((trucksList.size() - 1) * 500) +
                nbConstraintDist * 50000 +
                nbConstraintQuantity * 10000 +
                nbConstraintDuraton * 1000 +
                (nbMissingVisits+nbMultipleVisits) * 100000;
    }
}
