package fr.uga.project.electricvehicledelivery.heuristics;

import fr.uga.project.electricvehicledelivery.domain.InstanceSpecifications;
import fr.uga.project.electricvehicledelivery.domain.Solution;
import fr.uga.project.electricvehicledelivery.domain.SpotLink;
import fr.uga.project.electricvehicledelivery.domain.Spots;
import fr.uga.project.electricvehicledelivery.utils.SortUtil;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Heuristique permettant de générer une solution optimale
 * @author Antoine Angoulvant - Andréas Dedieu Meille
 */
@Deprecated
public class PowerHeuristic implements IHeuristics {

    private Spots spots;
    private InstanceSpecifications instance;
    private ArrayList<SpotLink<Double>> sortedDistance;
    private ArrayList<SpotLink<Integer>> sortedTimes;

    public PowerHeuristic(InstanceSpecifications instance, Spots spots){
        SortUtil sort = new SortUtil();
        this.spots = spots;
        this.instance = instance;
        this.sortedDistance = sort.sortMatrix(spots.getDistances());
        this.sortedTimes = sort.sortMatrix(spots.getTimes());
    }

    public SpotLink<Double> getOptimisedDistance(int costumerDeparture, ArrayList<Integer> spotsAlreadyShipped){
        return this.sortedDistance.stream()
                .filter((spotLink) -> spotLink.customer1 == costumerDeparture )
                .filter((spotLink) -> !spotsAlreadyShipped.contains(spotLink.customer1))
                .filter((spotLink) -> !spotsAlreadyShipped.contains(spotLink.customer2))
                // The first one founded is the lower one since we already sorted the matrix
                .findFirst()
                .orElse(SpotLink.DEFAULT());
    }

    public SpotLink<Integer>getOptimisedTime(int costumerDeparture, ArrayList<Integer> spotsAlreadyShipped){
        return this.sortedTimes.stream()
                .filter((spotLink) -> spotLink.customer1 == costumerDeparture )
                .filter((spotLink) -> !spotsAlreadyShipped.contains(spotLink.customer1))
                .filter((spotLink) -> !spotsAlreadyShipped.contains(spotLink.customer2))
                .findFirst()
                .orElse(SpotLink.DEFAULT());
    }

    public Solution run(){
        // TODO : times

        int currentCapacity = 0;
        int currentSpot = 0;
        int currentDistance = 0;
        ArrayList<Integer> demands = new ArrayList<>(Arrays.asList(spots.getDemands()));
        ArrayList<Integer> spotAlreadyShipped = new ArrayList<>();

        compute(demands, currentCapacity, currentSpot, currentDistance, spotAlreadyShipped);
        return null;
    }

    private void compute(ArrayList<Integer> demands, int currentCapacity, int currentSpot, float currentDistance, ArrayList<Integer> spotAlreadyShipped) {
        if (demands.size() == 0 || currentCapacity > instance.getCapacity() || currentDistance > instance.getMaxDist()){
            System.out.println("Stopping");
            return;
        }

        SpotLink<Double> nextSpot = this.getOptimisedDistance(currentSpot, spotAlreadyShipped);
        currentDistance+= nextSpot.value;
        if (demands.get(nextSpot.customer2) != null){
            currentCapacity+= demands.get(nextSpot.customer2);
            if (currentCapacity > instance.getCapacity()){
                // return overchage etc
            }
            demands.set(nextSpot.customer2, null);
        }
        spotAlreadyShipped.add(currentSpot);

        System.out.println("I'll drive from customer n° "+nextSpot.customer1+" to customer "+nextSpot.customer2);
        System.out.println("This is a distance of "+nextSpot.value+", at this time i would drive already "+currentDistance);
        System.out.println("I got a capacity of "+currentCapacity+" of "+instance.getCapacity());

        compute(demands, currentCapacity, nextSpot.customer2, currentDistance, spotAlreadyShipped);
    }
}
