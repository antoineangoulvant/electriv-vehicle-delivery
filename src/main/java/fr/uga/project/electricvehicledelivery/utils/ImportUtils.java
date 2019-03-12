package fr.uga.project.electricvehicledelivery.utils;


import fr.uga.project.electricvehicledelivery.domain.InstanceSpecifications;
import org.ini4j.Ini;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Classe utilitaire d'import des fichiers
 * Réalisé par Antoine Angoulvant et Andréas Dedieu Meille
 */
public class ImportUtils {
    public static InstanceSpecifications vehicleParse(String path){
        InstanceSpecifications instanceSpecifications = null;
        try {
            Ini iniFile = new Ini(new File(path));
            instanceSpecifications = new InstanceSpecifications(
                    Integer.parseInt(iniFile.get("Vehicle","max_dist")),
                    Integer.parseInt(iniFile.get("Vehicle","capacity")),
                    Integer.parseInt(iniFile.get("Vehicle","charge_fast")),
                    Integer.parseInt(iniFile.get("Vehicle","charge_midium")),
                    Integer.parseInt(iniFile.get("Vehicle", "charge_slow")),
                    iniFile.get("Vehicle", "start_time"),
                    iniFile.get("Vehicle", "end_time")
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return instanceSpecifications;
    }

    public static ArrayList<ArrayList<Float>> coordsParse(String assetPath){
        String pathFile_coords = "coords.txt";

        ArrayList<ArrayList<Float>> coordsMatrix = new ArrayList<>();
        Scanner input = null;

        try {
            input = new Scanner(new File(assetPath + "/"+ pathFile_coords));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while (input.hasNextLine()) {
            Scanner colReader = new Scanner(input.nextLine());
            ArrayList col = new ArrayList();
            if(colReader.hasNext()){
                String[] currentLine = colReader.next().split(",");
                col.addAll(Arrays.asList(currentLine));
            }
            coordsMatrix.add(col);
        }
        return coordsMatrix;
    }

    public static ArrayList<ArrayList<Float>> distancesParse(String assetPath){
        String pathFile_distances = "distances.txt";

        ArrayList<ArrayList<Float>> distancesMatrix = new ArrayList<>();
        Scanner input = null;

        try {
            input = new Scanner(new File(assetPath + "/"+ pathFile_distances));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        while (input.hasNextLine()) {
            Scanner colReader = new Scanner(input.nextLine());
            ArrayList col = new ArrayList();
            while (colReader.hasNext()) {
                col.add(colReader.next());
            }
            distancesMatrix.add(col);
        }
        return distancesMatrix;
    }

    public static ArrayList<ArrayList<Integer>> timesParse(String assetPath){
        String pathFile_times = "times.txt";

        ArrayList<ArrayList<Integer>> timesMatrix = new ArrayList<>();
        Scanner input = null;

        try {
            input = new Scanner(new File(assetPath + "/"+ pathFile_times));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        while (input != null && input.hasNextLine()) {
            Scanner colReader = new Scanner(input.nextLine());
            ArrayList col = new ArrayList();
            while (colReader.hasNextInt()) {
                col.add(colReader.nextInt());
            }
            timesMatrix.add(col);
        }
        return timesMatrix;
    }

    public static ArrayList<Integer> demandsParse(String assetPath) {
        String pathFile_times = "demandes.txt";

        ArrayList<Integer> demandsArray = new ArrayList<>();
        Scanner input = null;

        try {
            input = new Scanner(new File(assetPath + "/"+ pathFile_times));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        while (input != null && input.hasNextInt()) {
            demandsArray.add(input.nextInt());
        }
        return demandsArray;
    }
}
