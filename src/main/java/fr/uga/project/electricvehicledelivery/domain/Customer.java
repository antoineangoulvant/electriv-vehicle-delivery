package fr.uga.project.electricvehicledelivery.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Classe représentant un client qui un point spécifique
 * @author Antoine Angoulvant - Andréas Dedieu Meille
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Customer extends Spot {
    /** Nombre de sacs à livrer */
    private int demand;

    /**
     * Méthode retournant la durée totale de la livraison des sacs
     * @return durée totale de la livraison des sacs
     */
    public int getDeliveryDuration() {
        return demand * 10 + 5 * 60;
    }

    public String toString(){
        return this.getId() + " - {" + this.getX() + ";" + this.getY() + "} - Demand = " + this.demand;
    }
}
