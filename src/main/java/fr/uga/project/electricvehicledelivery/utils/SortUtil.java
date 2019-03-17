package fr.uga.project.electricvehicledelivery.utils;

import fr.uga.project.electricvehicledelivery.domain.SpotLink;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SortUtil implements Comparator<Number>{

    public int compare(Number a, Number b){
        return new BigDecimal(a.toString()).compareTo(new BigDecimal(b.toString()));
    }

    // Inspired by selection sort
    public <T> ArrayList<SpotLink<T>> sortMatrix(T[][] matrix){
        int totalNumberOfValues = matrix.length * matrix[0].length;
        int toto = 0;
        for (int i = 0; i < 11; i++){
            toto = toto + matrix[i].length;
        }
        ArrayList<SpotLink<T>> sorted = new ArrayList<>();

        int sourceIndexLine = 0;
        int sourceIndexCol = 0;
        while (sorted.size() < totalNumberOfValues){
            // find the smallest number
            int copyIndexLine = sourceIndexLine;
            int copyIndexCol = sourceIndexCol;

            for (int line = 0; line < matrix.length; line++){
                for (int col = 0; col < matrix[line].length; col++){
                    if ((matrix[line][col] != null)&& (matrix[copyIndexLine][copyIndexCol] != null) && (compare((Number)(matrix[copyIndexLine][copyIndexCol]), (Number) (matrix[line][col]) )) > 0){
                        copyIndexLine = line;
                        copyIndexCol = col;
                    }
                }
            }
            if (matrix[copyIndexLine][copyIndexCol] != null){
                sorted.add(new SpotLink<>(copyIndexLine, copyIndexCol, matrix[copyIndexLine][copyIndexCol]));
                System.out.println("Adding "+matrix[copyIndexLine][copyIndexCol]+ " to couple <"+copyIndexLine+","+copyIndexCol+">");
                //totalNumberOfTimes--;
                matrix[copyIndexLine][copyIndexCol] = null;
                System.out.println(sorted.size());
            }

            //System.out.println("IM A "+copyIndexLine+" AND "+copyIndexCol);

            // Incrementation
            if (((sourceIndexCol+1) % (matrix[0].length)) ==0){
                sourceIndexCol = 0;
                sourceIndexLine++;
            }else{
                sourceIndexCol++;
            }
            if (((sourceIndexLine+1) % (matrix.length +1)) ==0){
                sourceIndexLine = 0;
                sourceIndexCol = 0;
            }else{

            }

        }

        // Remove the matrix diag
        List<SpotLink> toRemove = sorted.stream()
                .filter((tSpotLink) -> tSpotLink.customer1 == tSpotLink.customer2)
                .collect(Collectors.toList());

        sorted.removeAll(toRemove);

        return sorted;
    }

    public static <T> List<SpotLink<T>> getAllSpotLinksWithCustomer1(ArrayList<SpotLink<T>> spotLinks, int customer1){
        return spotLinks.stream()
                .filter((sl) -> sl.customer1 == customer1)
                .collect(Collectors.toList());
    }




        public static ArrayList<SpotLink<Integer>> sortTimes(Integer[][] times){
        int totalNumberOfTimes = times.length * times[0].length;
        int toto = 0;
        for (int i = 0; i < 11; i++){
            toto = toto + times[i].length;
        }
        ArrayList<SpotLink<Integer>> timesSorted = new ArrayList<>();

        int sourceIndexLine = 0;
        int sourceIndexCol = 0;
        while (timesSorted.size() < totalNumberOfTimes){
            // find the smallest number
            int copyIndexLine = sourceIndexLine;
            int copyIndexCol = sourceIndexCol;

            for (int line = 0; line < times.length; line++){
                for (int col = 0; col < times[line].length; col++){
                    if ((times[line][col] != null)&& (times[copyIndexLine][copyIndexCol] != null) && (times[copyIndexLine][copyIndexCol] > times[line][col])){
                        copyIndexLine = line;
                        copyIndexCol = col;
                    }
                }
            }
            if (times[copyIndexLine][copyIndexCol] != null){
                timesSorted.add(new SpotLink<>(copyIndexLine, copyIndexCol, times[copyIndexLine][copyIndexCol]));
                System.out.println("Adding "+times[copyIndexLine][copyIndexCol]+ " to couple <"+copyIndexLine+","+copyIndexCol+">");
                //totalNumberOfTimes--;
                times[copyIndexLine][copyIndexCol] = null;
                System.out.println(timesSorted.size());
            }

            //System.out.println("IM A "+copyIndexLine+" AND "+copyIndexCol);

            if (((sourceIndexCol+1) % (times[0].length)) ==0){
                sourceIndexCol = 0;
                sourceIndexLine++;
            }else{
                sourceIndexCol++;
            }
            if (((sourceIndexLine+1) % (times.length +1)) ==0){
                sourceIndexLine = 0;
                sourceIndexCol = 0;
            }else{

            }

        }

        return timesSorted;

    }

}
