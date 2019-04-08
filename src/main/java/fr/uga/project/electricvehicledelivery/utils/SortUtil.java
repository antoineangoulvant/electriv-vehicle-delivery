package fr.uga.project.electricvehicledelivery.utils;

import com.sun.deploy.util.ArrayUtil;
import fr.uga.project.electricvehicledelivery.domain.SpotLink;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.nio.channels.FileLock;
import java.util.*;
import java.util.stream.Collectors;

public class SortUtil implements Comparator<Number>{

    public int compare(Number a, Number b){
        return new BigDecimal(a.toString()).compareTo(new BigDecimal(b.toString()));
    }

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

    public List<Integer> neighbor_sortMatrix(Float[][] matrix) {
        //matrix = removeDiagonalFromMatrix(matrix);
        matrix = removeWareHouse(matrix);
        int mainIndex=0, floatingIndex=0;
        float pretendValue;
        List<Integer> optimisedList = buildDefaultList(matrix);
        float optimisedValue = getOptimisedValue(matrix, optimisedList);
        do {
            if (mainIndex==floatingIndex){
                floatingIndex++;
                if (floatingIndex >= optimisedList.size()){
                    break;
                }else{
                    continue;
                }

            }
            // swap
            swap(mainIndex, floatingIndex, optimisedList);

            // compute optimised value
            pretendValue = getOptimisedValue(matrix, optimisedList);
            System.out.println("Array : "+optimisedList.toString()+"\nPretendValue : "+pretendValue+"\n OptimisedValue : "+optimisedValue+"\nmainIndex : "+mainIndex+"\nfloatingIndex : "+floatingIndex+"\n");
            if (pretendValue < optimisedValue){
                optimisedValue = pretendValue;
                // reset values
                mainIndex = 0;
                floatingIndex = 1;
                System.out.println(">> OptimisedValue = "+optimisedValue+", resetting mainIndex and floatingIndex\n");
            }else{
                swap(floatingIndex, mainIndex, optimisedList);
                if (floatingIndex == optimisedList.size()-1){
                    mainIndex++;
                    floatingIndex = 0;
                }else{
                    floatingIndex++;
                }
            }
        }while (mainIndex < optimisedList.size());

        return optimisedList;

    }

    private <T> Float[][] removeWareHouse(T[][] matrix) {
        Float[][] tmp = new Float[matrix.length-1][matrix[0].length-1];
        for (int i = 0; i <= matrix.length-2; i++){
            for (int y = 0; y <= matrix.length-2; y++){
                tmp[i][y] = (Float) matrix[i][y];
            }
        }
        return tmp;
    }

    private void swap(int i, int y, List<Integer> optimisedList) {
        int tmp;
        tmp = optimisedList.get(i);
        optimisedList.set(i, optimisedList.get(y));
        optimisedList.set(y, tmp);
    }

    private Float[][] removeDiagonalFromMatrix(Float[][] matrix) {
        int i =1, y;
        for (; i < matrix.length; i++){
            y =0;
            while (y < i){
                matrix[i][y] = 0f;
                y++;
            }

        }
        return matrix;
    }

    private float getOptimisedValue(Float[][] matrix, List<Integer> optimisedList) {
        float total = 0;
        for (int i = 0; i+1 < optimisedList.size(); i++){
            if (matrix[optimisedList.get(i)][optimisedList.get(i+1)] == 0){
                continue;
            }
            total += matrix[optimisedList.get(i)][optimisedList.get(i+1)];
        }
        return total;
    }

    private List<Integer> buildDefaultList(Float[][] matrix) {
        List<Integer> res = new ArrayList<>();
        // Adding the warehouse at the beginning
        //res.add(matrix.length-1);
        for (int i = 0; i < matrix.length; i++){
            res.add(i);
        }
        return res;
    }

}
