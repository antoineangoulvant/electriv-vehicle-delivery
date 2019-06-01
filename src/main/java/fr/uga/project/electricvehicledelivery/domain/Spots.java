package fr.uga.project.electricvehicledelivery.domain;

import fr.uga.project.electricvehicledelivery.utils.ImportUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


/**
 * Classe représentant les données des points de livraison
 * @author Antoine Angoulvant - Andréas Dedieu Meille
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Spots {
    /** Tableau des coordonnées */
    private Double[][] coords;
    /** Tableau des distances entre chaque point */
    private Double[][] distances;
    /** Tableau des temps entre chaque point */
    private Integer[][] times;
    /** Tableau des demandes de chaque client */
    private Integer[] demands;
    /** Liste des clients */
    private List<Customer> customers;
    /** Entrepôt */
    private Spot warehouse;

    /**
     * Constructeur de la classe
     * @param assetPath path d'accès aux assets
     */
    public Spots(String assetPath) {
        this.coords = importCoords(assetPath);
        this.distances = importDistances(assetPath);
        this.times = importTimes(assetPath);
        this.demands = importDemands(assetPath);

        this.customers = generateCustomers();
        this.warehouse = generateWarehouse();
    }

    /**
     * Méthode permettant d'importer les temps entre les points
     * @param assetPath chemin d'accès
     * @return tableau des temps entre chaque point
     */
    private Integer[][] importTimes(String assetPath) {
        return ImportUtils.timesParse(assetPath);
    }

    /**
     * Méthode permettant d'importer les distances entre les points
     * @param assetPath chemin d'accès
     * @return tableau des distances entre chaque point
     */
    private Double[][] importDistances(String assetPath) {
        return ImportUtils.distancesParse(assetPath);
    }

    /**
     * Méthode permettant d'importer les coordonnées des points
     * @param assetPath chemin d'accès
     * @return tableau des coordonnées
     */
    private Double[][]  importCoords(String assetPath) {
        return ImportUtils.coordsParse(assetPath);
    }

    /**
     * Méthode permettant d'importer les demandes
     * @param assetPath chemin d'accès
     * @return tableau des demandes
     */
    private Integer[] importDemands(String assetPath) {
        return ImportUtils.demandsParse(assetPath);
    }

    /**
     * Méthode permettant de générer les clients à partir des données
     * @return liste des clients
     */
    private List<Customer> generateCustomers(){
        List<Customer> customers = new ArrayList<>();

        for(int i = 0 ; i < demands.length ; i++ ){
            Customer temp = new Customer();
            temp.setId(i);
            temp.setX(coords[i][0]);
            temp.setY(coords[i][1]);
            temp.setDemand(demands[i]);
            customers.add(temp);
        }

        return customers;
    }

    /**
     * Méthode permettant de générer l'entrepôt avec ses informations
     * @return point de l'entrepôt
     */
    private Spot generateWarehouse(){
        Spot temp = new Spot();
        temp.setId(coords.length - 1);
        temp.setX(coords[temp.getId()][0]);
        temp.setY(coords[temp.getId()][1]);
        return temp;
    }

    public String toString() {
        return "Coords : \n------------------\n" + getCoords().toString()+
                "\nDistances : \n------------------\n" + getDistances().toString() +
                "\nTimes : \n------------------\n" + getTimes().toString() +
                "\nDemands : \n------------------\n"+ getDemands().toString();
    }
}
