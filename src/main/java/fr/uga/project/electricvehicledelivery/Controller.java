package fr.uga.project.electricvehicledelivery;

import fr.uga.project.electricvehicledelivery.domain.InstanceSpecifications;
import fr.uga.project.electricvehicledelivery.domain.Spots;
import fr.uga.project.electricvehicledelivery.heuristics.*;
import fr.uga.project.electricvehicledelivery.utils.Constants;
import fr.uga.project.electricvehicledelivery.utils.ImportUtils;
import fr.uga.project.electricvehicledelivery.view.GUI;
import lombok.Getter;

import java.sql.Time;
import java.util.Arrays;

/**
 * Classe centrale de l'application
 * Réalisé par Antoine Angoulvant et Andréas Dedieu Meille
 */
@Getter
public class Controller {
    private InstanceSpecifications instanceSpecifications;
    private Spots spot;

    private Controller() {
        /** SORTING TESTING**/
        /**
         SortUtil util = new SortUtil();
         ArrayList<SpotLink<Float>> toto = util.sortMatrix(spot.get_distances());
         ArrayList<SpotLink<Integer>> tata = util.sortMatrix(spot.get_times());
         **/
        new GUI(this);
    }

    public void launchHeuristic(String heuristic){
        Arrays.stream(HeuristicsEnum.values()).forEach(h -> {
            if(heuristic.equals(h.toString())){
                IHeuristics temp = HeuristicsFactory.getHeuristic(h, this.instanceSpecifications, this.spot);
                temp.run();
            }
        });
    }

    public void loadInstance(String path){
        this.instanceSpecifications = ImportUtils.vehicleParse(path+"/vehicle.ini");
        this.spot = new Spots(path);
    }

    public static void main(String[] args){
        new Controller();
    }
}
