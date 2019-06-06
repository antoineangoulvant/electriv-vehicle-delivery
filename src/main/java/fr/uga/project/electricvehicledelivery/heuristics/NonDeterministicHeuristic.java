package fr.uga.project.electricvehicledelivery.heuristics;

import fr.uga.project.electricvehicledelivery.domain.*;
import fr.uga.project.electricvehicledelivery.utils.Constants;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Heuristique non déterministe du problème de livraison avec des camions electriques
 * @author Antoine Angoulvant - Andréas Dedieu Meille
 */
public class NonDeterministicHeuristic implements IHeuristics {
    /**
     * Caractéristiques de l'instance
     */
    private InstanceSpecifications instance;
    /**
     * Clients et entrepôt de l'instance
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

    public NonDeterministicHeuristic(InstanceSpecifications instance, Spots spots) {
        this.instance = instance;
        this.spots = spots;
        this.customersIdToDeliver = this.spots.getCustomers().stream().map(Customer::getId).collect(Collectors.toList());
        this.trucks = new ArrayList<>();
    }

    @Override
    public Solution run() {
        Spot actualSpot = spots.getWarehouse();
        Truck actualTruck = new Truck();
        int actualCharge = 0;
        double actualDistance = 0.0;
        int actualDuration = 0;

        while(!customersIdToDeliver.isEmpty()){
            Pair<Integer, Double> tempPair = this.findRandomSpot(actualSpot.getId());
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

        System.out.println("Solution :");
        this.trucks.stream().map(Truck::getDeliveryPlanning).forEach(System.out::println);
        solution.updateDistanceAndDuration(this.spots);

        return solution;
    }

    /**
     * Méthode permettant de récupérer un point aléatoirement
     * @param spotId id du point actuelle
     * @return une pair contenant l'id du point aléatoire et
     *      la distance entre le point passer en paramètre et le point aléatoire
     */
    private Pair<Integer,Double> findRandomSpot(int spotId){
        int random = (int) (Math.random() * customersIdToDeliver.size());
        int id = customersIdToDeliver.get(random);
        return new Pair<>(id, this.spots.getDistances()[spotId][id]);
    }
}
