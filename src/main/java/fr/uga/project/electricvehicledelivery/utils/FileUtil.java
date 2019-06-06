package fr.uga.project.electricvehicledelivery.utils;

import fr.uga.project.electricvehicledelivery.domain.Solution;
import fr.uga.project.electricvehicledelivery.domain.Spots;
import fr.uga.project.electricvehicledelivery.domain.Truck;
import javafx.util.Pair;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Antoine Angoulvant - Andréas Dedieu Meille
 */
public class FileUtil {
    /**
     * Add the "C" spot at the beginning of each list from the parameter list, and convert all into string
     * Also, replace the max value in list by "R" (for recharges)
     *
     * @param list
     * @return
     */
    public static List<List<String>> AddWarepointSpot(List<List<Integer>> list) {
        if (list == null)
            throw new IllegalArgumentException();
        List<List<String>> strList = new ArrayList<>();

        for (List<Integer> subList : list) {
            List<String> tmp = new ArrayList<>();
            for (int i = 0; i < subList.size(); i++) {
                if (subList.get(i) >= subList.size())
                    throw new IllegalArgumentException("An item from a list is higher than the size of his list");
                if (subList.get(i) == subList.size() - 1) {
                    tmp.add("R");
                } else {
                    tmp.add(subList.get(i).toString());
                }
            }
            tmp.add(0, "C");
            strList.add(tmp);
        }
        return strList;
    }

    /**
     * Write the strListinto the file named fileName under the folder Results
     *
     * @param strList
     * @param fileName
     */
    public static void WriteCompleteTravel(List<List<String>> strList, String fileName) {
        if (fileName == "")
            fileName = "toto.txt";

        String filePath = Constants.RESULTS_FOLDER_PATH + fileName;
        for (List<String> subList : strList) {

            try (FileWriter fw = new FileWriter(filePath, true);
                 BufferedWriter bw = new BufferedWriter(fw);
                 PrintWriter out = new PrintWriter(bw)) {
                out.println(subList.stream().collect(Collectors.joining(",")));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Méthode permettant de sauvegarder les résultats d'une heuristique dans un fichier txt
     * @param solution résultat de l'heuristique
     * @param fileName nom du fichier
     */
    public static void saveSolution(Solution solution, String fileName) {
        if (fileName == "") throw new IllegalArgumentException("Le nom de fichier ne peut pas être vide");

        String filePath = Constants.RESULTS_FOLDER_PATH + fileName + "-" + UUID.randomUUID();
        for (Truck truck : solution.getTrucksList()) {
            try (FileWriter fw = new FileWriter(filePath, true);
                 BufferedWriter bw = new BufferedWriter(fw);
                 PrintWriter out = new PrintWriter(bw)) {
                out.println(truck.getDeliveryPlanning().stream().collect(Collectors.joining(",")));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Méthode permettant de sauvegarder la solution sous format JSON qui pourrait être utilisé
     * sur la page situé sur l'URL energie.antoine-angoulvant.fr
     * @param solution solution de l'heuristique
     * @param filename nom du fichier
     * @param spots points de l'instances
     */
    public static void saveSolutionAsJson(Solution solution, String filename, Spots spots) {
        try {
            JSONObject jsonObject = new JSONObject();

            /** Liste des camions */
            JSONArray trucksArray = new JSONArray();
            for(Truck truck : solution.getTrucksList()){
                JSONObject truckObject = new JSONObject();
                JSONArray deliveryArray = new JSONArray();
                for(String id : truck.getDeliveryPlanning()){
                    JSONObject object = new JSONObject();
                    object.put("id",id);
                    Pair<Double,Double> coordinate;
                    if(id.equals("C") || id.equals("R")){
                        coordinate = spots.getWarehouseCoordinate();
                    } else {
                        coordinate = spots.getCustomerCoordinate(Integer.parseInt(id));
                    }
                    object.put("x",coordinate.getKey());
                    object.put("y",coordinate.getValue());
                    deliveryArray.add(object);
                }
                truckObject.put("Spots", deliveryArray);
                trucksArray.add(truckObject);
            }
            jsonObject.put("Trucks", trucksArray);

            Pair<Double,Double> coordinate = spots.getWarehouseCoordinate();
            JSONObject warehouse = new JSONObject();
            warehouse.put("x", coordinate.getKey());
            warehouse.put("y", coordinate.getValue());
            jsonObject.put("Warehouse", warehouse);

            String filePath = Constants.RESULTS_FOLDER_PATH + "JSON/" + filename + "-" + UUID.randomUUID() + ".json";

            FileWriter fileWriter = new FileWriter(filePath, true);
            fileWriter.write(jsonObject.toJSONString());
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
