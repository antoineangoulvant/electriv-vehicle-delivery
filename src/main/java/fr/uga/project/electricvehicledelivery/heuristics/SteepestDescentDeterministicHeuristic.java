package fr.uga.project.electricvehicledelivery.heuristics;

import fr.uga.project.electricvehicledelivery.domain.InstanceSpecifications;
import fr.uga.project.electricvehicledelivery.domain.Solution;
import fr.uga.project.electricvehicledelivery.domain.Spots;

/**
 * Cette heuristique permet de faire une descente sur les résultats de l'heuristique déterministe
 * @author Antoine Angoulvant - Andréas Dedieu Meille
 */
public class SteepestDescentDeterministicHeuristic implements IHeuristics {
    /**
     * Caractéristiques de l'instance
     */
    private Solution solution;
    /**
     * Clients et entrepôt de l'instance
     */
    private Spots spots;
    private InstanceSpecifications instance;

    public SteepestDescentDeterministicHeuristic(InstanceSpecifications instance, Spots spots){
        DeterministicHeuristic heuristic = new DeterministicHeuristic(instance, spots);
        this.solution = heuristic.run();
        this.instance = instance;
        this.spots = spots;
    }

    @Override
    public Solution run() {
        this.solution.updateDistanceAndDuration(this.spots);
        return null;
    }
}
