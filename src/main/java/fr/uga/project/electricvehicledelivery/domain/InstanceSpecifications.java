package fr.uga.project.electricvehicledelivery.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Classe modélisant les caractéristiques d'une instance
 * @author Antoine Angoulvant et Andréas Dedieu Meille
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class InstanceSpecifications {
    /** Distance maximale avant recharge */
    private int maxDist;
    /** Capacité maximale de sacs du camion */
    private int capacity;
    /** Durée de charge rapide en minutes */
    private int chargeFast;
    /** Durée de charge classique en minutes */
    private int chargeMedium;
    /** Durée de charge lente en minutes */
    private int chargeSlow;
    /** Heure de début des livraisons */
    private String startTime;
    /** Heure de fin des livraisons */
    private String endTime;

    /**
     * Méthode retournant l'heure de début en secondes
     * @return heure de début en secondes
     */
    public int getStartTimeSeconds() {
       return ((Integer.parseInt(this.startTime.substring(0,this.startTime.indexOf(":"))) * 3600) +
               (Integer.parseInt(this.startTime.substring(this.startTime.indexOf(":")+1)) * 60));
    }

    /**
     * Méthode retournant l'heure de fin en secondes
     * @return heure de fin en secondes
     */
    public int getEndTimeToSeconds() {
        return ((Integer.parseInt(this.endTime.substring(0,this.endTime.indexOf(":"))) * 3600) +
                (Integer.parseInt(this.endTime.substring(this.endTime.indexOf(":")+1)) * 60));
    }

    /**
     * Méthode retournant la durée totale ouvrée de livraison en secondes
     * @return durée totale ouvrée de livraison en secondes
     */
    public int getMaximumDuration() {
        return getEndTimeToSeconds() - getStartTimeSeconds();
    }

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
