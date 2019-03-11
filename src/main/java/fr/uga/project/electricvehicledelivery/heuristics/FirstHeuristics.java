package fr.uga.project.electricvehicledelivery.heuristics;

import fr.uga.project.electricvehicledelivery.domain.InstanceSpecifications;
import fr.uga.project.electricvehicledelivery.domain.Spots;
import lombok.NonNull;

public class FirstHeuristics {
    public FirstHeuristics(@NonNull InstanceSpecifications instanceSpecifications, @NonNull Spots spots){
        int demandsSum = 0;
        for(int i = 0; i < spots.get_demands().size(); i++){
            if(demandsSum < instanceSpecifications.getCapacity()){
                demandsSum += spots.get_demands().get(i);
            }
        }

        System.out.println(demandsSum);
    }
}
