package fr.uga.project.electricvehicledelivery;

import fr.uga.project.electricvehicledelivery.domain.InstanceSpecifications;
import fr.uga.project.electricvehicledelivery.domain.Spots;
import fr.uga.project.electricvehicledelivery.heuristics.*;
import fr.uga.project.electricvehicledelivery.utils.Constants;
import fr.uga.project.electricvehicledelivery.utils.ImportUtils;

/**
 * Classe centrale de l'application
 * Réalisé par Antoine Angoulvant et Andréas Dedieu Meille
 */
public class Controller {
    private Controller() {
        InstanceSpecifications instanceSpecifications = ImportUtils.vehicleParse(Constants.Assets.ASSET_LYON0.getInstance()+"/vehicle.ini");
        System.out.println(instanceSpecifications);

        Spots spot = new Spots(Constants.Assets.ASSET_LYON0.getInstance());


        /** SORTING TESTING**/
        /**
         SortUtil util = new SortUtil();
         ArrayList<SpotLink<Float>> toto = util.sortMatrix(spot.get_distances());
         ArrayList<SpotLink<Integer>> tata = util.sortMatrix(spot.get_times());
         **/

        //IHeuristics power = HeuristicsFactory.getHeuristic(HeuristicsEnum.PowerHeuristics, instanceSpecifications, spot);
        //power.run();

        IHeuristics neighbor = HeuristicsFactory.getHeuristic(HeuristicsEnum.NeighborHeuristics, instanceSpecifications, spot);
        neighbor.run();

//        IHeuristics localSearch = HeuristicsFactory.getHeuristic(HeuristicsEnum.LocalSearchHeuristic, instanceSpecifications, spot);
//        localSearch.run();
    }

    public static void main(String[] args){
        new Controller();
    }
}
