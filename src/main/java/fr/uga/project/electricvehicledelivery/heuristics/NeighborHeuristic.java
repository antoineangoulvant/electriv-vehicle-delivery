package fr.uga.project.electricvehicledelivery.heuristics;

import fr.uga.project.electricvehicledelivery.domain.InstanceSpecifications;
import fr.uga.project.electricvehicledelivery.domain.Spots;
import fr.uga.project.electricvehicledelivery.utils.FileUtil;
import fr.uga.project.electricvehicledelivery.utils.SortUtil;

import java.util.List;
import java.util.Random;

public class NeighborHeuristic implements IHeuristics {

    Spots spots;
    InstanceSpecifications instance;

    public NeighborHeuristic(InstanceSpecifications instance, Spots spots){
        this.instance = instance;
        this.spots = spots;
    }


    public void run() {
        SortUtil sort = new SortUtil();
        sort.setDistances(this.spots.getDistances());

        //sort.neighbor_Distances_Optimized(this.spots.getDistances());
        //List<List<Integer>> list = sort.neighbor_Distances_Pick(3, null);
        Float[][] distancesWithoutWare = sort.removeWareHouse_Float();
        List<Integer> defaultList = sort.buildDefaultList(distancesWithoutWare);

        List<List<Integer>> list = sort.neighbor_Distances_Pick(3, defaultList, 0);


        List<List<String>> strList = FileUtil.AddInitialWarepointSpot(list);
        FileUtil.WriteCompleteTravel(strList, "neighbor.txt");


    }

}
