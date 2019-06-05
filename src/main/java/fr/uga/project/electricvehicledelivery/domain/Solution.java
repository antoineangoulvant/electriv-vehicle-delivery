package fr.uga.project.electricvehicledelivery.domain;

import fr.uga.project.electricvehicledelivery.heuristics.HeuristicsEnum;
import fr.uga.project.electricvehicledelivery.utils.FileUtil;
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
    private List<Truck> trucksList;
    private int nbConstraintDist;
    private int nbConstraintQuantity;
    private int nbConstraintDuraton;
    private int nbMissingVisits;
    private int nbMultipleVisits;

    /**
     * Constructeur de la classe solution
     * @param truckList liste des camions
     */
    public Solution(List<Truck> truckList){
        this.trucksList = truckList;
        this.nbConstraintDist = 0;
        this.nbConstraintQuantity = 0;
        this.nbConstraintDuraton = 0;
        this.nbMissingVisits = 0;
        this.nbMultipleVisits = 0;
    }

    /**
     * Méthode permettant de calculer la durée totale en minute
     * @return
     */
    public Double getTotalDuration() {
        return trucksList.stream().mapToDouble(t -> t.getDuration()/60).sum();
    }

    /**
     * Méthode permettant de calculer la distance totale
     * @return distance totale
     */
    public Double getTotalDistance() {
        return trucksList.stream().mapToDouble(Truck::getDistance).sum();
    }

    /**
     * Méthode permettant d'évaluer la solution afin de donner un "score"
     * @return score de la solution
     */
    public Double evaluate(){
        return this.getTotalDistance() +
                this.getTotalDuration() +
                ((trucksList.size() - 1) * 500) +
                nbConstraintDist * 50000 +
                nbConstraintQuantity * 10000 +
                nbConstraintDuraton * 1000 +
                (nbMissingVisits+nbMultipleVisits) * 100000;
    }

    /**
     * Méthode permettant d'appeler la méthode de sauvegarde de la solution dans un fichier txt
     * @param fileName nom du ficher
     */
    public void save(String fileName){
        FileUtil.saveSolution(this, fileName);
    }

    /**
     * Méthode permettant de mettre à jour la distance et la durée totale des livraisons d'un camion
     * afin de pouvoir réévaluer la solution
     * @param spots liste de points de l'instance
     */
    public void updateDistanceAndDuration(Spots spots) {
        Spot actualSpot = null;
        for(Truck truck : trucksList){
            System.out.println("Start distance : " + truck.getDistance());
            System.out.println("Start duration : " + truck.getDuration());
            int duration = 0;
            double distance = 0;
            for(String id : truck.getDeliveryPlanning()){
                if(actualSpot == null){
                    if(id.equals("C") || id.equals("R")){
                        actualSpot = spots.getWarehouse();
                    } else {
                        actualSpot = spots.getCustomers().stream().filter(c ->
                                c.getId() == Integer.parseInt(id)).findFirst().orElse(null);
                    }
                } else {
                    Spot tempSpot = null;
                    if(id.equals("C") || id.equals("R")){
                        tempSpot = spots.getWarehouse();
                    } else {
                        tempSpot = spots.getCustomers().stream().filter(c ->
                                c.getId() == Integer.parseInt(id)).findFirst().orElse(null);
                    }
                    distance += spots.getDistances()[actualSpot.getId()][tempSpot.getId()];
                    duration += spots.getTimes()[actualSpot.getId()][tempSpot.getId()];
                    actualSpot = tempSpot;
                }
            }
            truck.setDuration(duration);
            truck.setDistance(distance);
            System.out.println("New distance : " + truck.getDistance());
            System.out.println("New duration : " + truck.getDuration());
        }
    }
}
