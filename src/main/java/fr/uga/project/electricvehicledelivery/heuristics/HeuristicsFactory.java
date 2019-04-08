package fr.uga.project.electricvehicledelivery.heuristics;

import fr.uga.project.electricvehicledelivery.domain.InstanceSpecifications;
import fr.uga.project.electricvehicledelivery.domain.Spots;

public class HeuristicsFactory {

    public static IHeuristics getHeuristic(HeuristicsEnum enumHeuristics, InstanceSpecifications instance, Spots spot){
        switch (enumHeuristics) {
            case FirstHeuristics:
                return new FirstHeuristics();
            case PowerHeuristics:
                return new PowerHeuristic(instance, spot);
            case NeighborHeuristics:
                return new NeighborHeuristic(instance, spot);
        }
        return null;
    }
}
