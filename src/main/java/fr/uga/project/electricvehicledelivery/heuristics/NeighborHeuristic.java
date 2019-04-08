package fr.uga.project.electricvehicledelivery.heuristics;

import fr.uga.project.electricvehicledelivery.domain.InstanceSpecifications;
import fr.uga.project.electricvehicledelivery.domain.Spots;
import fr.uga.project.electricvehicledelivery.utils.SortUtil;

public class NeighborHeuristic implements IHeuristics {

    Spots spots;
    InstanceSpecifications instance;

    public NeighborHeuristic(InstanceSpecifications instance, Spots spots){
        this.instance = instance;
        this.spots = spots;
    }


    public void run() {
        SortUtil sort = new SortUtil();
        sort.neighbor_sortMatrix(this.spots.getDistances());
    }
}
