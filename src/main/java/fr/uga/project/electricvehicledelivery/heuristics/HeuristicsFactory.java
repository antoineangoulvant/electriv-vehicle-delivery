package fr.uga.project.electricvehicledelivery.heuristics;

import fr.uga.project.electricvehicledelivery.domain.InstanceSpecifications;
import fr.uga.project.electricvehicledelivery.domain.Spots;

public class HeuristicsFactory {

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
