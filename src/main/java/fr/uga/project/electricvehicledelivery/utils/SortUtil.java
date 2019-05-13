package fr.uga.project.electricvehicledelivery.utils;

import fr.uga.project.electricvehicledelivery.domain.SpotLink;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class SortUtil implements Comparator<Number>{

    public Float[][] distances;

    public int compare(Number a, Number b){
        return new BigDecimal(a.toString()).compareTo(new BigDecimal(b.toString()));
    }

    //region PowerHeuristic
    // Inspired by selection sort
    public <T> ArrayList<SpotLink<T>> sortMatrix(T[][] matrix){
        int totalNumberOfValues = matrix.length * matrix[0].length;

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
            }

        }

        // Remove the matrix diagonal
        List<SpotLink> toRemove = sorted.stream()
                .filter((tSpotLink) -> tSpotLink.customer1 == tSpotLink.customer2)
                .collect(Collectors.toList());

        sorted.removeAll(toRemove);

        return sorted;
    }
    //endregion

//    public List<Integer> neighbor_Distances_Optimized(Float[][] distances) {
//        //distances = removeDiagonalFromMatrix(distances);
//        List<Integer> optimisedList = new ArrayList<>();
//        distances = removeWareHouse_Float(distances);
//        int mainIndex=0, floatingIndex=0;
//        float pretendValue;
//        List<Integer> currentList = buildDefaultList(distances);
//        float optimisedValue = getOptimisedValue(distances, currentList);
//        do {
//            if (mainIndex==floatingIndex){
//                floatingIndex++;
//                if (floatingIndex >= currentList.size()){
//                    break;
//                }else{
//                    continue;
//                }
//
//            }
//            // swap
//            swap(mainIndex, floatingIndex, currentList);
//
//            // compute optimised value
//            pretendValue = getOptimisedValue(distances, currentList);
//            System.out.println("Array : "+currentList.toString()+"\nPretendValue : "+pretendValue+"\n OptimisedValue : "+optimisedValue+"\nmainIndex : "+mainIndex+"\nfloatingIndex : "+floatingIndex+"\n");
//            if (pretendValue < optimisedValue){
//                optimisedValue = pretendValue;
//                optimisedList = currentList;
//                // reset values
//                mainIndex = 0;
//                floatingIndex = 1;
//                System.out.println(">> OptimisedValue = "+optimisedValue+", resetting mainIndex and floatingIndex\n");
//            }else{
//                swap(floatingIndex, mainIndex, currentList);
//                if (floatingIndex == currentList.size()-1){
//                    mainIndex++;
//                    floatingIndex = 0;
//                }else{
//                    floatingIndex++;
//                }
//            }
//        }while (mainIndex < currentList.size());
//
//        System.out.println("Done. Optimised List : "+optimisedList.toString());
//        return optimisedList;
//
//    }




    public void setDistances(Float[][] distances) {
        this.distances = distances;
    }
}
