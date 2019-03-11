package fr.uga.project.electricvehicledelivery;

import fr.uga.project.electricvehicledelivery.domain.InstanceSpecifications;
import fr.uga.project.electricvehicledelivery.domain.Spots;
import fr.uga.project.electricvehicledelivery.heuristics.FirstHeuristics;
import fr.uga.project.electricvehicledelivery.utils.Constants;
import fr.uga.project.electricvehicledelivery.utils.ImportUtils;

public class Controller {
    private Controller() {
        InstanceSpecifications instanceSpecifications = ImportUtils.vehicleParse(Constants.ASSET_LYON1+"/vehicle.ini");
        System.out.println(instanceSpecifications);
        Spots spots = new Spots(Constants.ASSET_LYON1);
        new FirstHeuristics(instanceSpecifications, spots);
    }

    public static void main(String[] args){
        new Controller();
    }
}
