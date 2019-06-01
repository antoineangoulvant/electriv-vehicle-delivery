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
 *
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
    private List<Customer> customersIdToDeliver;

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
        this.customersIdToDeliver = this.spots.getCustomers();
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
                        this.customersIdToDeliver.remove(tempCustomer);
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
                    // Temps pour rentrer au dépot
                    actualDuration += spots.getTimes()[tempCustomer.getId()][spots.getWarehouse().getId()];
                    // Temps de recharge, on considère que l'on utilise toujours la recharge rapide
                    actualDuration += instance.getChargeFast() * 60;
                    actualDistance = 0.0;

                }
            } else {
                actualTruck.addToPlanning(Constants.TRUCK_LOADING);
                actualDuration += spots.getTimes()[tempCustomer.getId()][spots.getWarehouse().getId()];
                actualSpot = spots.getWarehouse();
                actualCharge = 0;
            }
        }
        this.trucks.add(actualTruck);

        this.trucks.stream().map(Truck::getDeliveryPlanning).forEach(System.out::println);
    }

    /**
     * Méthode permettant de renvoyer l'id du point le plus proche du point passer en paramètre
     *
     * @param spotId id du point dont l'on veut le plus proche
     * @return id du point le plus proche
     */
    private Pair<Integer,Double> findNearestSpotFromSpot(int spotId) {
        List<Integer> ids = customersIdToDeliver.stream().map(Customer::getId).collect(Collectors.toList());
        int nearestId = -1;
        Double nearestDist = -1.0;
        for(Integer i : ids){
            if (nearestDist == -1.0) {
                nearestId = i;
                nearestDist = this.spots.getDistances()[spotId][i];
            }
            if (this.spots.getDistances()[spotId][i] < nearestDist
                    && i != spotId && ids.indexOf(i) != -1) {
                nearestId = i;
                nearestDist = this.spots.getDistances()[spotId][i];
            }
        }
        return new Pair<>(nearestId, nearestDist);
    }
}
