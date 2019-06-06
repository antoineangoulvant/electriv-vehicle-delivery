package fr.uga.project.electricvehicledelivery.heuristics;

import fr.uga.project.electricvehicledelivery.domain.InstanceSpecifications;
import fr.uga.project.electricvehicledelivery.domain.Solution;
import fr.uga.project.electricvehicledelivery.domain.Spots;

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
        return null;
    }
}
