package fr.uga.project.electricvehicledelivery.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe modélisant les caractéristiques d'une instance
 * Réalisé par Antoine Angoulvant et Andréas Dedieu Meille
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstanceSpecifications {
    private int maxDist;
    private int capacity;
    private int chargeFast;
    private int chargeMedium;
    private int chargeSlow;
    private String startTime;
    private String endTime;

    public String toString(){
        return "\n\tCaractéristiques de l'instance \n\n" +
                "Distance maximale : " + this.maxDist +
                "\nCapacité maximale : " + this.capacity +
                "\nCharge lente : " + this.chargeSlow +
                "\nCharge moyenne : " + this.chargeMedium +
                "\nCharge rapide : " + this.chargeFast +
                "\nHeure de début : " + this.startTime +
                "\nHeure de fin : " + this.endTime;
    }
}
