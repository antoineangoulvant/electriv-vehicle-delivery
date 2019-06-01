package fr.uga.project.electricvehicledelivery.heuristics;

import fr.uga.project.electricvehicledelivery.domain.InstanceSpecifications;
import fr.uga.project.electricvehicledelivery.domain.Spots;

/**
 * Factory des différentes heuristiques
 * @author Antoine Angoulvant - Andréas Dedieu Meille
 */
public class HeuristicsFactory {
    /**
     * Méthode permettant d'exécuter les différentes heuristiques
     * @param enumHeuristics énumérations de toutes les heuristiques
     * @param instance caractéristiques de l'instance
     * @param spots points utilisés par l'heuristique
     * @return heuristique
     */
    public static IHeuristics getHeuristic(HeuristicsEnum enumHeuristics, InstanceSpecifications instance, Spots spots){
        switch (enumHeuristics) {
            case PowerHeuristics:
                return new PowerHeuristic(instance, spots);
            case NeighborHeuristics:
                return new NeighborHeuristic(instance, spots);
            case LocalSearchHeuristic:
                return new LocalSearchHeuristic(instance, spots);
            case DeterministicHeuristic:
                return new DeterministicHeuristic(instance, spots);
            case NonDeterministicHeuristic:
                return new NonDeterministicHeuristic(instance, spots);
            default:
                throw new IllegalArgumentException("Unknow heuristic");
        }
    }
}
