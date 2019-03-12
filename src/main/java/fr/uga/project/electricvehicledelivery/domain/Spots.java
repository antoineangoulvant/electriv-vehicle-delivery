package fr.uga.project.electricvehicledelivery.domain;

import fr.uga.project.electricvehicledelivery.utils.ImportUtils;

import java.util.ArrayList;

/**
 * Classe représentant les données des points de livraison
 * Réalisé par Antoine Angoulvant et Andréas Dedieu Meille
 */
public class Spots {
    private ArrayList<ArrayList<Float>> coords;
    private ArrayList<ArrayList<Float>> distances;
    private ArrayList<ArrayList<Integer>> times;
    private ArrayList<Integer> demands;

    public Spots(String assetName) {
        importSpots(assetName);
    }

    private void importSpots(String assetPath) {
        this.coords = importCoords(assetPath);
        this.distances = importDistances(assetPath);
        this.times = importTimes(assetPath);
        this.demands = importDemands(assetPath);
    }

    private ArrayList<ArrayList<Integer>> importTimes(String assetPath) {
        return ImportUtils.timesParse(assetPath);
    }

    private ArrayList<ArrayList<Float>> importDistances(String assetPath) {
        return ImportUtils.distancesParse(assetPath);
    }

    private ArrayList<ArrayList<Float>>  importCoords(String assetPath) {
        return ImportUtils.coordsParse(assetPath);
    }

    private ArrayList<Integer> importDemands(String assetPath) {
        return ImportUtils.demandsParse(assetPath);
    }

    public ArrayList<ArrayList<Float>> getCoords() {
        return coords;
    }

    public ArrayList<ArrayList<Float>> getDistances() {
        return distances;
    }

    public ArrayList<ArrayList<Integer>> getTimes() {
        return times;
    }

    public ArrayList<Integer> getDemands() {
        return demands;
    }

    public String toString() {
        return "Coords : \n------------------\n" + getCoords().toString()+
                "\nDistances : \n------------------\n" + getDistances().toString() +
                "\nTimes : \n------------------\n" + getTimes().toString() +
                "\nDemands : \n------------------\n"+ getDemands().toString();
    }
}
