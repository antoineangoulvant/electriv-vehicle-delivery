package fr.uga.project.electricvehicledelivery.heuristics;

import fr.uga.project.electricvehicledelivery.domain.InstanceSpecifications;
import fr.uga.project.electricvehicledelivery.domain.Spots;
import lombok.NonNull;

/**
 * Première heuristique
 * Réalisé par Antoine Angoulvant et Andréas Dedieu Meille
 */
public class FirstHeuristics {
    public FirstHeuristics(@NonNull InstanceSpecifications instanceSpecifications, @NonNull Spots spots){
        int demandsSum = 0;
        for(int i = 0; i < spots.getDemands().size(); i++){
            if(demandsSum < instanceSpecifications.getCapacity()){
                demandsSum += spots.getDemands().get(i);
            }
        }

        System.out.println(demandsSum);
    }
}
