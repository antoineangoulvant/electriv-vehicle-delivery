package fr.uga.project.electricvehicledelivery.utils;


import fr.uga.project.electricvehicledelivery.domain.InstanceSpecifications;
import org.ini4j.Ini;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class ImportUtils {
    public static InstanceSpecifications vehicleParse(String path){
        InstanceSpecifications instanceSpecifications = null;
        try {
            Ini iniFile = new Ini(new File(path));
            instanceSpecifications = new InstanceSpecifications(
                    Integer.parseInt(iniFile.get("Vehicle","max_dist")),
                    Integer.parseInt(iniFile.get("Vehicle","capacity")),
                    Integer.parseInt(iniFile.get("Vehicle","charge_fast")),
                    Integer.parseInt(iniFile.get("Vehicle","charge_medium")),
                    Integer.parseInt(iniFile.get("Vehicle", "charge_slow")),
                    iniFile.get("Vehicle", "start_time"),
                    iniFile.get("Vehicle", "end_time")
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return instanceSpecifications;
    }

    public static Float[][] coordsParse(String assetPath){
        String pathFile_times = "coords.txt";
        String fullPath = assetPath + "/"+ pathFile_times;

        Scanner input = null;
        Float[][] coordsMatrix;

        try {
            input = new Scanner(new File(fullPath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        int colSize = 2;
        int lineSize = getLineSize(fullPath);

        coordsMatrix = new Float[lineSize][colSize];

        int currentLinePosition = 0;

        while (input != null && input.hasNextLine()) {
            int currentColPosition = 0;
            Scanner colReader = new Scanner(input.nextLine());
            while (colReader.hasNext()) {
                String[] coords = colReader.next().split(",");
                coordsMatrix[currentLinePosition][currentColPosition] = Float.parseFloat(coords[0]);
                currentColPosition++;
                coordsMatrix[currentLinePosition][currentColPosition] = Float.parseFloat(coords[1]);
                currentLinePosition++;
            }

        }
        return coordsMatrix;
    }

    public static Float[][] distancesParse(String assetPath){
        String pathFile_times = "distances.txt";
        String fullPath = assetPath + "/"+ pathFile_times;

        Scanner input = null;
        Float[][] distancesMatrix;

        try {
            input = new Scanner(new File(fullPath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        int colSize = getColSize(fullPath);
        int lineSize = getLineSize(fullPath);

        distancesMatrix = new Float[lineSize][colSize];

        int currentLinePosition = 0;

        while (input != null && input.hasNextLine()) {
            int currentColPosition = 0;
            Scanner colReader = new Scanner(input.nextLine());
            while (colReader.hasNext()) {
                distancesMatrix[currentLinePosition][currentColPosition] = Float.parseFloat(colReader.next());
                currentColPosition++;
            }
            currentLinePosition++;
        }
        return distancesMatrix;
    }

    public static Integer[][] timesParse(String assetPath){
        String pathFile_times = "times.txt";
        String fullPath = assetPath + "/"+ pathFile_times;

        //ArrayList<ArrayList<Integer>> timesMatrix = new ArrayList<>();
        Scanner input = null;
        Integer[][] timesMatrix;

        try {
            input = new Scanner(new File(fullPath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        int colSize = getColSize(fullPath);
        int lineSize = getLineSize(fullPath);

        timesMatrix = new Integer[lineSize][colSize];

        int currentLinePosition = 0;

        while (input != null && input.hasNextLine()) {
            int currentColPosition = 0;
            Scanner colReader = new Scanner(input.nextLine());
            while (colReader.hasNextInt()) {
                timesMatrix[currentLinePosition][currentColPosition] = colReader.nextInt();
                currentColPosition++;
            }
            currentLinePosition++;
        }
        return timesMatrix;
    }

    private static int getLineSize(String fullPath) {
        Scanner input = null;
        int lineSize = 0;
        try{
            input= new Scanner(new File(fullPath));
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }

        while (input != null && input.hasNextLine()){
            input.nextLine();
            lineSize++;
        }
        return lineSize;
    }

    private static int getColSize(String fullPath) {
        Scanner input = null;
        int colSize = 0;
        try{
            input= new Scanner(new File(fullPath));
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }

        if (input != null && input.hasNextLine()){
            Scanner colReader = new Scanner(input.nextLine());
            while (colReader.hasNext()){
                colSize++;
                colReader.next();
            }
        }
        return colSize;
    }

    public static Integer[] demandsParse(String assetPath) {
        String pathFile_times = "demandes.txt";
        String full_path = assetPath + "/"+ pathFile_times;

        Integer[] demandsArray;
        Scanner input = null;

        try {
            input = new Scanner(new File(full_path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        int lineSize = getLineSize(full_path);

        demandsArray = new Integer[lineSize];

        int currentLineposition = 0;
        while (input != null && input.hasNextInt()) {
            demandsArray[currentLineposition]= input.nextInt();
            currentLineposition++;
        }
        return demandsArray;
    }

    /***
     * Link the sorted values of the matrix in param with the position in the ArrayList
     * Ex : distanceMatrix[4,5] = 5; distancesMatrix[2,3] = 2
     * >> return Map<2, [2,3]> Map<5, [4,5]>
     * @param distancesMatrix
     * @return
    /*     *//*
    public static Map<Integer, Tuple<Integer, Integer>> sortDistances(ArrayList<ArrayList<Float>> distancesMatrix){

        Map<Integer, ArrayList<ArrayList<Integer>>> resultMap = new HashMap<>();

        // Convert matrix into simple array
        Float[][] distanceMatrixArray;
        distanceMatrixArray = new Float[distancesMatrix.size()][distancesMatrix.get(0).size()];

        for (int i = 0; i < distancesMatrix.size(); i++){
            for (int y = 0; y < distancesMatrix.get(i).size() ;y++){
                distanceMatrixArray[i][y] = distancesMatrix.get(i).get(y);
            }
        }




        return null;
    }*/
}
