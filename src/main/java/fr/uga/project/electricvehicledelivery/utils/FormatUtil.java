package fr.uga.project.electricvehicledelivery.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe contenant des méthodes de formatages communes
 */
public class FormatUtil {
    /**
     * Méthode permettant de formatter un temps en secondes sous un format plus simple à lire
     * @param seconds temps en seconde
     * @return temps formatté
     */
    public static String formatTime(int seconds){
        int hours = seconds / 3600;
        int minutes = (seconds / 60)-(hours*60);
        int secondsLeft = seconds - (hours*3600) - (minutes*60);
        return hours + "h " + minutes + "m " + secondsLeft + "s";
    }

    /**
     * Méthode permettant de pivoter deux listes de point de livraison
     * @param toSwap liste à pivoter
     * @param a id du premier élément à pivoter
     * @param b id du second élément à pivoter
     * @return liste pivoté
     */
    public static List<List<String>> swapDelivery(List<List<String>> toSwap, int a, int b){
        if(a > toSwap.size() || b > toSwap.size()){
            throw new IllegalArgumentException("Les id de swap doivent être inférieure à la taille de la liste");
        }
        List<String> temp = toSwap.get(a);
        toSwap.set(a, toSwap.get(b));
        toSwap.set(b, temp);
        return toSwap;
    }

    /**
     * Méthode permettant de reconstruire la liste des livraisons qui a été séparer
     * @param splitDeliveryList Liste des listes de point de livraison
     * @return Liste complète des livraisons
     */
    public static List<String> buildDeliveryList(List<List<String>> splitDeliveryList){
        List<String> delivery = new ArrayList<>();
        for(List<String> list : splitDeliveryList){
            delivery.addAll(list);
            delivery.add("C");
        }
        delivery.remove(delivery.size()-1);
        return delivery;
    }
}
