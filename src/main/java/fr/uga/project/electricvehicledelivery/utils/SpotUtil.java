package fr.uga.project.electricvehicledelivery.utils;

import java.util.ArrayList;
import java.util.List;

public class SpotUtil {

    /**
     * Ajoute les points de recharge nécessaire dans le tableau. A un point donné, si le prochain arret dépasse les capacités
     * de la distanceMax du véhicule, insert dans le tableau un rapatriement au point de recharge (qui est le numéro le
     * plus haut de la liste list).
     * @param distances le tableau des distances entre points
     * @param list la liste à analyser
     * @param distanceMax la distanceMax du véhicule
     * @return
     */
    public static List<Integer> AddRechargeStops(Double[][] distances, List<Integer> list, int distanceMax){
        float distanceTotale = 0;
        List<Integer> resultList = new ArrayList<>(list);
        for(int i = 0; i < list.size()-1; i++){

            // The first condition is unvalid
            if ( ((distanceTotale+distances[i][0]) > distanceMax) || ((distanceTotale+distances[i][i+1]) > distanceMax)){
                System.out.println("Retour à la base ! (A la position "+i+", avant "+list.get(i)+") Le dépasse ma capacité "+distanceMax+" car j'ai déjà fait "+distanceTotale+" et que le prochain trajet est à "+distances[i][i+1]);
                resultList.add(i+1, list.size());
                distanceTotale = 0;
            }else{
                distanceTotale += distances[i][i+1];
            }
        }
        return resultList;
    }
}
