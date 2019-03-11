package fr.uga.project.electricvehicledelivery.domain;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InstanceSpecifications {
    private int maxDist;
    private int capacity;
    private int chargeFast;
    private int chargeMedium;
    private int chargeSlow;
    private String startTime;
    private String endTime;

    public InstanceSpecifications(int maxDist, int capacity, int chargeFast, int chargeMedium, int chargeSlow, String startTime, String endTime) {
        this.maxDist = maxDist;
        this.capacity = capacity;
        this.chargeFast = chargeFast;
        this.chargeMedium = chargeMedium;
        this.chargeSlow = chargeSlow;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getCapacity() {
        return capacity;
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
