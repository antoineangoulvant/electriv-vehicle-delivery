package fr.uga.project.electricvehicledelivery.heuristics;

import fr.uga.project.electricvehicledelivery.domain.*;
import fr.uga.project.electricvehicledelivery.utils.Constants;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Heuristique déterministe permettant de proposer une solution
 * Répond à la question
 * @author Antoine Angoulvant - Andréas Dedieu Meille
 */
public class DeterministicHeuristic implements IHeuristics {
    /**
     * Caractéristiques de l'instance
     */
    private InstanceSpecifications instance;
    /**
     * Clients et entrepôts de l'instance
     */
    private Spots spots;
    /**
     * Liste des clients restants
     */
    private List<Integer> customersIdToDeliver;

    /**
     * Liste des camions
     */
    private List<Truck> trucks;

    /**
     * Constructeur de l'heuristique
     * @param instance caractéristiques de l'instance
     * @param spots points de l'instance
     */
    DeterministicHeuristic(InstanceSpecifications instance, Spots spots) {
        this.instance = instance;
        this.spots = spots;
        this.customersIdToDeliver = this.spots.getCustomers().stream().map(Customer::getId).collect(Collectors.toList());
        this.trucks = new ArrayList<>();
    }

    @Override
    public void run() {
        Spot actualSpot = spots.getWarehouse();
        Truck actualTruck = new Truck();
        int actualCharge = 0;
        double actualDistance = 0.0;
        int actualDuration = 0;

        while(!customersIdToDeliver.isEmpty()){
            Pair<Integer, Double> tempPair = this.findNearestSpotFromSpot(actualSpot.getId());
            Customer tempCustomer = spots.getCustomers().stream().filter(c ->
                    c.getId() == tempPair.getKey()).findFirst().orElse(null);
            if(actualCharge + tempCustomer.getDemand() < instance.getCapacity()){
                if(actualDistance + tempPair.getValue() < instance.getMaxDist()) {
                    if(actualDuration + spots.getTimes()[actualSpot.getId()][tempPair.getKey()]
                        + tempCustomer.getDeliveryDuration() < instance.getMaximumDuration()){
                        actualCharge += tempCustomer.getDemand();
                        actualDistance += tempPair.getValue();
                        actualDuration += spots.getTimes()[actualSpot.getId()][tempPair.getKey()]
                                + tempCustomer.getDeliveryDuration();

                        actualSpot = tempCustomer;

                        actualTruck.addToPlanning(Integer.toString(tempCustomer.getId()));
                        actualTruck.addToDistance(tempPair.getValue());
                        actualTruck.addToDuration(spots.getTimes()[actualSpot.getId()][tempPair.getKey()]
                                + tempCustomer.getDeliveryDuration());

                        this.customersIdToDeliver.remove(this.customersIdToDeliver.indexOf(tempCustomer.getId()));
                    } else {
                        this.trucks.add(actualTruck);
                        actualTruck = new Truck();
                        actualCharge = 0;
                        actualDistance = 0.0;
                        actualDuration = 0;
                        actualSpot = spots.getWarehouse();
                    }
                } else {
                    actualTruck.addToPlanning(Constants.BATTERY_LOADING);
                    int durationToAdd = spots.getTimes()[tempCustomer.getId()][spots.getWarehouse().getId()]
                            + instance.getChargeFast() * 60;
                    // Temps pour rentrer au dépot et pour recharger avec la recharge rapide
                    actualDuration += durationToAdd ;
                    actualDistance = 0.0;

                    actualTruck.addToDistance(spots.getDistances()[tempCustomer.getId()][spots.getWarehouse().getId()]);
                    actualTruck.addToDuration(durationToAdd);
                }
            } else {
                actualTruck.addToPlanning(Constants.TRUCK_LOADING);
                actualDuration += spots.getTimes()[tempCustomer.getId()][spots.getWarehouse().getId()];
                actualSpot = spots.getWarehouse();
                actualTruck.addToDuration(spots.getTimes()[tempCustomer.getId()][spots.getWarehouse().getId()]);
                actualTruck.addToDistance(spots.getDistances()[tempCustomer.getId()][spots.getWarehouse().getId()]);
                actualCharge = 0;
            }
        }
        this.trucks.add(actualTruck);

        Solution solution = new Solution(this.trucks);
        solution.save(this.getClass().getSimpleName());

        System.out.println("Solution :");
        this.trucks.stream().map(Truck::getDeliveryPlanning).forEach(System.out::println);
        System.out.println("Score de la solution : " + solution.evaluate());
    }

    /**
     * Méthode permettant de renvoyer l'id du point le plus proche du point passer en paramètre
     *
     * @param spotId id du point dont l'on veut le plus proche
     * @return id du point le plus proche
     */
    private Pair<Integer,Double> findNearestSpotFromSpot(int spotId) {
        int nearestId = -1;
        Double nearestDist = -1.0;
        for(Integer i : customersIdToDeliver){
            if (nearestDist == -1.0) {
                nearestId = i;
                nearestDist = this.spots.getDistances()[spotId][i];
            }
            if (this.spots.getDistances()[spotId][i] < nearestDist
                    && i != spotId && customersIdToDeliver.indexOf(i) != -1) {
                nearestId = i;
                nearestDist = this.spots.getDistances()[spotId][i];
            }
        }
        return new Pair<>(nearestId, nearestDist);
    }
}
