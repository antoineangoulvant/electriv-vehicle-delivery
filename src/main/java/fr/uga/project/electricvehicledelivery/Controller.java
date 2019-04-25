package fr.uga.project.electricvehicledelivery;

import fr.uga.project.electricvehicledelivery.domain.InstanceSpecifications;
import fr.uga.project.electricvehicledelivery.domain.Spots;
import fr.uga.project.electricvehicledelivery.heuristics.*;
import fr.uga.project.electricvehicledelivery.utils.Constants;
import fr.uga.project.electricvehicledelivery.utils.ImportUtils;
import fr.uga.project.electricvehicledelivery.view.GUI;

import java.util.Arrays;

/**
 * Classe centrale de l'application
 * Réalisé par Antoine Angoulvant et Andréas Dedieu Meille
 */
public class Controller {
    private InstanceSpecifications instanceSpecifications;
    private Spots spot;

    private Controller() {
        this.instanceSpecifications = ImportUtils.vehicleParse(Constants.Assets.ASSET_LYON0.getInstance()+"/vehicle.ini");
        System.out.println(instanceSpecifications);

        this.spot = new Spots(Constants.Assets.ASSET_LYON0.getInstance());

        /** SORTING TESTING**/
        /**
         SortUtil util = new SortUtil();
         ArrayList<SpotLink<Float>> toto = util.sortMatrix(spot.get_distances());
         ArrayList<SpotLink<Integer>> tata = util.sortMatrix(spot.get_times());
         **/
        new GUI(this);
    }

    public void launchHeuristic(String heuristic){
        System.out.println(heuristic);
        Arrays.stream(HeuristicsEnum.values()).forEach(h -> {
            if(heuristic.equals(h.toString())){
                IHeuristics temp = HeuristicsFactory.getHeuristic(h, this.instanceSpecifications, this.spot);
                temp.run();
            }
        });
    }

    public static void main(String[] args){
        new Controller();
    }
}
