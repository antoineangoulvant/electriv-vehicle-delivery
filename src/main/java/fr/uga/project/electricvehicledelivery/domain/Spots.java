package fr.uga.project.electricvehicledelivery.domain;

import fr.uga.project.electricvehicledelivery.utils.ImportUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Classe représentant les données des points de livraison
 * Réalisé par Antoine Angoulvant et Andréas Dedieu Meille
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Spots {
    private Double[][] coords;
    private Double[][] distances;
    private Integer[][] times;
    private Integer[] demands;

    public Spots(String assetName) {
        importSpots(assetName);
    }

    private void importSpots(String assetPath) {
        this.coords = importCoords(assetPath);
        this.distances = importDistances(assetPath);
        this.times = importTimes(assetPath);
        this.demands = importDemands(assetPath);
    }

    private Integer[][] importTimes(String assetPath) {
        return ImportUtils.timesParse(assetPath);
    }

    private Double[][] importDistances(String assetPath) {
        return ImportUtils.distancesParse(assetPath);
    }

    private Double[][]  importCoords(String assetPath) {
        return ImportUtils.coordsParse(assetPath);
    }

    private Integer[] importDemands(String assetPath) {
        return ImportUtils.demandsParse(assetPath);
    }

    public String toString() {
        return "Coords : \n------------------\n" + getCoords().toString()+
                "\nDistances : \n------------------\n" + getDistances().toString() +
                "\nTimes : \n------------------\n" + getTimes().toString() +
                "\nDemands : \n------------------\n"+ getDemands().toString();
    }
}
