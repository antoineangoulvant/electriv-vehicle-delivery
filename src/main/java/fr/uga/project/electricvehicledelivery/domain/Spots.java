package fr.uga.project.electricvehicledelivery.domain;

import fr.uga.project.electricvehicledelivery.utils.ImportUtils;

import java.util.ArrayList;

public class Spots {
    private ArrayList<ArrayList<Float>> _coords;
    private ArrayList<ArrayList<Float>> _distances;
    private ArrayList<ArrayList<Integer>> _times;
    private ArrayList<Integer> _demands;

    public Spots(String assetName) {
        importSpots(assetName);
    }

    private void importSpots(String assetPath) {
        this._coords = importCoords(assetPath);
        this._distances = importDistances(assetPath);
        this._times = importTimes(assetPath);
        this._demands = importDemands(assetPath);
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

    public ArrayList<ArrayList<Float>> get_coords() {
        return _coords;
    }

    public ArrayList<ArrayList<Float>> get_distances() {
        return _distances;
    }

    public ArrayList<ArrayList<Integer>> get_times() {
        return _times;
    }

    public String toString() {
        return "Coords : \n------------------\n" + get_coords().toString()+
                "\nDistances : \n------------------\n" + get_distances().toString() +
                "\nTimes : \n------------------\n" + get_times().toString() +
                "\nDemands : \n------------------\n"+get_demands().toString();
    }

    public ArrayList<Integer> get_demands() {
        return _demands;
    }
}
