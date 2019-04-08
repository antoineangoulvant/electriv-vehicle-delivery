package fr.uga.project.electricvehicledelivery.heuristics;

import fr.uga.project.electricvehicledelivery.domain.InstanceSpecifications;
import fr.uga.project.electricvehicledelivery.domain.Spots;

public class HeuristicsFactory {

    public static IHeuristics getHeuristic(HeuristicsEnum enumHeuristics, InstanceSpecifications instance, Spots spots){
        switch (enumHeuristics) {
            case FirstHeuristics:
                return new FirstHeuristics();
            case PowerHeuristics:
                return new PowerHeuristic(instance, spots);
            case NeighborHeuristics:
                return new NeighborHeuristic(instance, spots);
            case LocalSearchHeuristic:
                return new LocalSearchHeuristic(instance, spots);
        }
        return null;
    }
}
