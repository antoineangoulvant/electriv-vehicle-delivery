package fr.uga.project.electricvehicledelivery.heuristics;

import fr.uga.project.electricvehicledelivery.domain.InstanceSpecifications;
import fr.uga.project.electricvehicledelivery.domain.Spots;

/**
 * Cette heuristique permet de faire une recherche locale sur les données de l'instance
 * @author Antoine Angoulvant - Andréas Dedieu Meille
 */
public class LocalSearchHeuristic implements IHeuristics {
    public LocalSearchHeuristic(InstanceSpecifications specifications, Spots spots){
        System.out.println(spots.getDistances().length);

        run();
    }

    @Override
    public void run() {

    }
}
