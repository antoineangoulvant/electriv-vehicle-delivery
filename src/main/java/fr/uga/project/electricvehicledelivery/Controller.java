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
 * @author Antoine Angoulvant - Andréas Dedieu Meille
 */
@Getter
public class Controller {
    private InstanceSpecifications instanceSpecifications;
    private Spots spots;

    private Controller() {
        /** SORTING TESTING**/
        /**
         SortUtil util = new SortUtil();
         ArrayList<SpotLink<Float>> toto = util.sortMatrix(spot.get_distances());
         ArrayList<SpotLink<Integer>> tata = util.sortMatrix(spot.get_times());
         **/
        new GUI(this);
    }

    /**
     * Méthode permettant de lancer une heuristique
     * @param heuristic heuristique à lancer
     */
    public void launchHeuristic(String heuristic){
        Arrays.stream(HeuristicsEnum.values()).forEach(h -> {
            if(heuristic.equals(h.toString())){
                IHeuristics temp = HeuristicsFactory.getHeuristic(h, this.instanceSpecifications, this.spots);
                temp.run();
            }
        });
    }

    /**
     * Méthode permettant de charger l'instance choisie par l'utilisateur
     * @param path chemin d'accès de l'instance
     */
    public void loadInstance(String path){
        this.instanceSpecifications = ImportUtils.vehicleParse(path+"/vehicle.ini");
        this.spots = new Spots(path);
    }

    public static void main(String[] args){
        new Controller();
    }
}
