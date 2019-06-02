package fr.uga.project.electricvehicledelivery.utils;

import fr.uga.project.electricvehicledelivery.domain.Solution;
import fr.uga.project.electricvehicledelivery.domain.Truck;

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
        List<List<String>> strList = new ArrayList<>();

        for (List<Integer> subList : list) {
            List<String> tmp = new ArrayList<>();
            for (int i = 0; i < subList.size(); i++) {
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
                out.println(subList);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

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
}
