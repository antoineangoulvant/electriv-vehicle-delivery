package fr.uga.project.electricvehicledelivery.heuristics;

import fr.uga.project.electricvehicledelivery.domain.InstanceSpecifications;
import fr.uga.project.electricvehicledelivery.domain.Solution;
import fr.uga.project.electricvehicledelivery.domain.Spots;
import fr.uga.project.electricvehicledelivery.domain.Truck;
import fr.uga.project.electricvehicledelivery.utils.FileUtil;
import fr.uga.project.electricvehicledelivery.utils.FormatUtil;
import javafx.scene.shape.TriangleMesh;

import java.util.ArrayList;
import java.util.List;

/**
 * Cette heuristique permet de faire une descente sur les résultats de l'heuristique déterministe
 *
 * @author Antoine Angoulvant - Andréas Dedieu Meille
 */
public class SteepestDescentDeterministicHeuristic implements IHeuristics {
    /**
     * Solution de l'heuristique déterministe
     */
    private Solution solution;
    /**
     * Clients et entrepôt de l'instance
     */
    private Spots spots;
    /**
     * Caractéristiques de l'instance
     */
    private InstanceSpecifications instance;

    public SteepestDescentDeterministicHeuristic(InstanceSpecifications instance, Spots spots) {
        DeterministicHeuristic heuristic = new DeterministicHeuristic(instance, spots);
        this.solution = heuristic.run();
        this.instance = instance;
        this.spots = spots;
    }

    @Override
    public Solution run() {
        this.solution.updateDistanceAndDuration(this.spots);
        List<Solution> firstNeighborhoodSolutions = this.firstNeighborhood();
        return null;
    }

    private List<Solution> firstNeighborhood() {
        List<List<List<String>>> splitDeliveryByTruck = new ArrayList<>();

        for (Truck truck : this.solution.getTrucksList()) {
            List<Integer> indices = new ArrayList<>();
            for (int i = 0; i < truck.getDeliveryPlanning().size(); i++) {
                if (truck.getDeliveryPlanning().get(i).equals("C")) {
                    indices.add(i);
                }
            }

            Truck newTruck = new Truck();
            if (indices.isEmpty()) {
                newTruck.setDeliveryPlanning(truck.getDeliveryPlanning());
                continue;
            }

            List<List<String>> splitDelivery = new ArrayList<>();

            if (indices.size() == 1) {
                splitDelivery.add(truck.getDeliveryPlanning().subList(0, indices.get(0)));
                splitDelivery.add(truck.getDeliveryPlanning().subList(indices.get(0) + 1,
                        truck.getDeliveryPlanning().size()));
            } else {
                int tempId = 0;
                for (int i = 0; i < indices.size(); i++) {
                    if (i == 0) {
                        splitDelivery.add(truck.getDeliveryPlanning().subList(0, indices.get(i)));
                        tempId = indices.get(i);
                        continue;
                    }
                    splitDelivery.add(truck.getDeliveryPlanning().subList(tempId + 1, indices.get(i)));
                    tempId = indices.get(i);
                    if (i == indices.size() - 1 && !truck.getDeliveryPlanning().subList(indices.get(i) + 1,
                            truck.getDeliveryPlanning().size()).isEmpty()) {
                        splitDelivery.add(truck.getDeliveryPlanning().subList(indices.get(i) + 1,
                                truck.getDeliveryPlanning().size()));
                    }
                }
            }
            splitDeliveryByTruck.add(splitDelivery);
            System.out.println(truck.getDeliveryPlanning());
            System.out.println(splitDelivery);
        }

        List<Solution> solutions = new ArrayList<>();
        int nbIteration = splitDeliveryByTruck.stream().mapToInt(List::size).min().getAsInt();
        for (int i = 0; i < nbIteration; i++) {
            List<Truck> newTrucks = new ArrayList<>();
            for (List<List<String>> split : splitDeliveryByTruck) {
                Truck truck = new Truck();
                truck.setDeliveryPlanning(FormatUtil.buildDeliveryList(FormatUtil.swapDelivery(split, 0, i)));
                newTrucks.add(truck);
            }
            Solution solution = new Solution(newTrucks);
            solution.updateDistanceAndDuration(this.spots);
            solutions.add(solution);
        }

        return solutions;
    }
}




