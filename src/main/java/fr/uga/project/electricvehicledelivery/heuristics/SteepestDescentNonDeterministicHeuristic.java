package fr.uga.project.electricvehicledelivery.heuristics;

import fr.uga.project.electricvehicledelivery.domain.InstanceSpecifications;
import fr.uga.project.electricvehicledelivery.domain.Solution;
import fr.uga.project.electricvehicledelivery.domain.Spots;
import fr.uga.project.electricvehicledelivery.domain.Truck;
import fr.uga.project.electricvehicledelivery.utils.HeuristicUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Cette heuristique permet de faire une descente sur les résultats de l'heuristique non déterministe
 * @author Antoine Angoulvant - Andréas Dedieu Meille
 */
public class SteepestDescentNonDeterministicHeuristic implements IHeuristics {
    /**
     * Caractéristiques de l'instance
     */
    private InstanceSpecifications instance;
    /**
     * Clients et entrepôt de l'instance
     */
    private Spots spots;
    /**
     * Solution de l'heuristique déterministe
     */
    private Solution solution;

    public SteepestDescentNonDeterministicHeuristic(InstanceSpecifications instance, Spots spots){
        NonDeterministicHeuristic heuristic = new NonDeterministicHeuristic(instance, spots);
        this.solution = heuristic.run();
        this.instance = instance;
        this.spots = spots;
    }

    @Override
    public Solution run() {
        this.solution.updateDistanceAndDuration(this.spots);
        Solution bestSolution = this.solution;

        List<Solution> firstNeighbourhoodSolutions = this.firstNeighbourhood();
        for (Solution solution : firstNeighbourhoodSolutions) {
            if (solution.evaluate() < bestSolution.evaluate()) {
                bestSolution = solution;
            }
        }

        List<Solution> secondNeighborhoodSolutions = this.secondNeighbourhood();
        for (Solution solution : secondNeighborhoodSolutions) {
            if (solution.evaluate() < bestSolution.evaluate()) {
                bestSolution = solution;
            }
        }

        List<Solution> thirdNeighborhoodSolutions = this.thirdNeighbourhood();
        for (Solution solution : thirdNeighborhoodSolutions) {
            if (solution.evaluate() < bestSolution.evaluate()) {
                bestSolution = solution;
            }
        }

        return bestSolution;
    }

    /**
     * Méthode permettant de calculer les différents voisinages
     *
     * @return liste des solutions du voisinage
     */
    private List<Solution> firstNeighbourhood() {
        List<List<List<String>>> splitDeliveryByTruck = new ArrayList<>();

        for (Truck truck : this.solution.getTrucksList()) {
            List<Integer> indices = new ArrayList<>();
            for (int i = 0; i < truck.getDeliveryPlanning().size(); i++) {
                if (truck.getDeliveryPlanning().get(i).equals("C")) {
                    indices.add(i);
                }
            }

            if (indices.isEmpty()) {
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
        }

        List<Solution> solutions = new ArrayList<>();
        int nbIteration = splitDeliveryByTruck.stream().mapToInt(List::size).min().getAsInt();
        for (int i = 0; i < nbIteration; i++) {
            List<Truck> newTrucks = new ArrayList<>();
            for (List<List<String>> split : splitDeliveryByTruck) {
                Truck truck = new Truck();
                truck.setDeliveryPlanning(HeuristicUtil.buildDeliveryList(HeuristicUtil.swapDelivery(split, 0, i)));
                newTrucks.add(truck);
            }
            Solution solution = new Solution(newTrucks);
            solution.updateDistanceAndDuration(this.spots);
            solutions.add(solution);
        }

        return solutions;
    }

    /**
     * Deuxième voisinage qui va intervertir le premier point avec le premier qui suit un chargement
     * @return Liste de solutions
     */
    private List<Solution> secondNeighbourhood() {
        List<List<Integer>> truckIndices = new ArrayList<>();
        for (Truck truck : this.solution.getTrucksList()) {
            List<Integer> indices = new ArrayList<>();
            for (int i = 0; i < truck.getDeliveryPlanning().size(); i++) {
                if (truck.getDeliveryPlanning().get(i).equals("C")) {
                    indices.add(i);
                }
            }
            if (!indices.isEmpty()) truckIndices.add(indices);
        }

        List<Solution> solutions = new ArrayList<>();
        int nbIteration = truckIndices.stream().mapToInt(List::size).min().getAsInt();
        for (int i = 0; i < nbIteration; i++) {
            List<Truck> newTrucks = new ArrayList<>();
            for (int y = 0; y < truckIndices.size(); y++) {
                Truck truck = new Truck();
                truck.setDeliveryPlanning(HeuristicUtil.swapFirstElementDelivery(
                        this.solution.getTrucksList().get(y)
                                .getDeliveryPlanning(), 0, truckIndices.get(y).get(i) + 1));
                newTrucks.add(truck);
            }
            Solution solution = new Solution(newTrucks);
            solution.updateDistanceAndDuration(this.spots);
            solutions.add(solution);
        }

        return solutions;
    }

    /**
     * Troisème voisinage qui va transformer le voisinage en déplacant le dernier entre chaque chargement en
     * dernier d'un autre
     * @return Liste de solutions
     */
    private List<Solution> thirdNeighbourhood() {
        List<List<Integer>> truckIndices = new ArrayList<>();
        for (Truck truck : this.solution.getTrucksList()) {
            List<Integer> indices = new ArrayList<>();
            for (int i = 0; i < truck.getDeliveryPlanning().size(); i++) {
                if (truck.getDeliveryPlanning().get(i).equals("C")) {
                    indices.add(i);
                }
            }
            if (!indices.isEmpty()) truckIndices.add(indices);
        }

        List<Solution> solutions = new ArrayList<>();
        int nbIteration = truckIndices.stream().mapToInt(List::size).min().getAsInt();
        for (int i = 0; i < nbIteration; i++) {
            if (i != nbIteration - 1) {
                List<Truck> newTrucks = new ArrayList<>();
                for (int y = 0; y < truckIndices.size(); y++) {
                    Truck truck = new Truck();

                    truck.setDeliveryPlanning(HeuristicUtil.swapFirstElementDelivery(
                            this.solution.getTrucksList().get(y).getDeliveryPlanning(),
                            truckIndices.get(y).get(i) - 1, truckIndices.get(y).get(i + 1) - 1));
                    newTrucks.add(truck);
                }
                Solution solution = new Solution(newTrucks);
                solution.updateDistanceAndDuration(this.spots);
                solutions.add(solution);
            }

        }
        return solutions;
    }
}
