package fr.uga.project.electricvehicledelivery.heuristics;

import fr.uga.project.electricvehicledelivery.domain.InstanceSpecifications;
import fr.uga.project.electricvehicledelivery.domain.Spots;
import fr.uga.project.electricvehicledelivery.utils.FileUtil;
import fr.uga.project.electricvehicledelivery.utils.SpotUtil;

import java.util.*;

public class NeighborHeuristic implements IHeuristics {

    Spots spots;
    InstanceSpecifications instance;

    public NeighborHeuristic(InstanceSpecifications instance, Spots spots){
        this.instance = instance;
        this.spots = spots;
    }

    //region NeighborHeuristic_FirstPick
    private List<Integer> neighbor_Distances_First_Pick (List<Integer> listToParse) throws Exception {

        if (listToParse == null || listToParse.size() == 0){
            throw new Exception("ListToParse is null or Empty");
        }
        
        Double[][] distances = this.spots.getDistances();

        List<Integer> firstList = getFirstNeighbor(listToParse);
        List<Integer> retainedList = new ArrayList<>(firstList);

        while(firstList != null){
            retainedList = new ArrayList<>(firstList);
            firstList = getFirstNeighbor(firstList);

        }

        retainedList = SpotUtil.AddRechargeStops(distances, retainedList, instance.getMaxDist());
        return retainedList;
    }

    private List<Integer> getFirstNeighbor(List<Integer> currentList) {
        Double[][] distances = this.spots.getDistances();
        float defaultValue = getOptimisedValue(distances, currentList), pretendValue;

        for (int i = 0; i+1 < currentList.size(); i++){
            swap(i, i+1, currentList);

            pretendValue = getOptimisedValue(distances, currentList);


            if (pretendValue < defaultValue){
                System.out.println("Array : "+currentList.toString()+"\nPretendValue : "+pretendValue+"\n OptimisedValue : "+defaultValue+"\nPosition in Array : "+i+"\n");

                System.out.println(" >> Adding list \n");

                return currentList;
            }

            swap(i, i+1, currentList);

        }

        return null;

    }

    //endregion

    // region NeighbotHeuristic_BestPick
    private List<Integer> neighbor_Distances_Best_Pick(List<Integer> listToParse) throws Exception {

        if (listToParse == null || listToParse.size() == 0){
            throw new Exception("ListToParse is null or Empty");
        }
        
        Double[][] distances = this.spots.getDistances();

        List<Integer> tmpList = getBestNeighbor(listToParse);
        List<Integer> bestList = new ArrayList<>(tmpList);

        while(tmpList != null){
            bestList = new ArrayList<>(tmpList);
            tmpList = getBestNeighbor(tmpList);

        }

        bestList = SpotUtil.AddRechargeStops(distances, bestList, instance.getMaxDist());
        return bestList;
    }

    private List<Integer> getBestNeighbor(List<Integer> currentList) {

        Double[][] distances = this.spots.getDistances();
        float defaultValue = getOptimisedValue(distances, currentList), pretendValue;
        List<Integer> currentBestList = new ArrayList<>();

        for (int i = 0; i+1 < currentList.size(); i++){
            swap(i, i+1, currentList);

            pretendValue = getOptimisedValue(distances, currentList);


            if (pretendValue < defaultValue){
                System.out.println("Array : "+currentList.toString()+"\nPretendValue : "+pretendValue+"\n OptimisedValue : "+defaultValue+"\nPosition in Array : "+i+"\n");
                System.out.println(" >> Adding list \n");

                defaultValue = pretendValue;
                currentBestList = new ArrayList<>(currentList);
            }

            swap(i, i+1, currentList);

        }

        if (currentBestList.size() == 0){
            return null;
        }

        return currentBestList;
    }

    //endregion

    //region TODO
    /**
     * TODO !
     * @param times
     * @return
     */
    public List<Integer> neighbor_Times_Optimised(Integer[][] times){
        times = removeWareHouse_Int(times);
        int mainIndex=0, floatingIndex=0;
        float pretendValue;
        List<Integer> optimisedList = new ArrayList<>();
        List<Integer> currentList = buildDefaultList(times);
        float optimisedValue = getOptimisedValue(times, currentList);
        do {
            if (mainIndex==floatingIndex){
                floatingIndex++;
                if (floatingIndex >= currentList.size()){
                    break;
                }else{
                    continue;
                }

            }
            // swap
            swap(mainIndex, floatingIndex, currentList);

            // compute optimised value
            pretendValue = getOptimisedValue(times, currentList);
            System.out.println("Array : "+currentList.toString()+"\nPretendValue : "+pretendValue+"\n OptimisedValue : "+optimisedValue+"\nmainIndex : "+mainIndex+"\nfloatingIndex : "+floatingIndex+"\n");
            if (pretendValue < optimisedValue){
                optimisedValue = pretendValue;
                optimisedList = currentList;

                // reset values
                mainIndex = 0;
                floatingIndex = 1;
                System.out.println(">> OptimisedValue = "+optimisedValue+", resetting mainIndex and floatingIndex\n");
            }else{
                swap(floatingIndex, mainIndex, currentList);
                if (floatingIndex == currentList.size()-1){
                    mainIndex++;
                    floatingIndex = 0;
                }else{
                    floatingIndex++;
                }
            }
        }while (mainIndex < currentList.size());

        System.out.println("Done. Optimised List : "+optimisedList.toString());
        return optimisedList;
    }

    public <T> Integer[][] removeWareHouse_Int(T[][] matrix) {
        Integer[][] tmp = new Integer[matrix.length-1][matrix[0].length-1];
        for (int i = 0; i <= matrix.length-2; i++){
            for (int y = 0; y <= matrix.length-2; y++){
                tmp[i][y] = (Integer) matrix[i][y];
            }
        }
        return tmp;
    }

    //endregion

    // region NeighborHeuristics_Helpers

    /**
     * Swaps two items i and y from the list optimisedList
     * @param i
     * @param y
     * @param optimisedList
     */
    private void swap(int i, int y, List<Integer> optimisedList) {
        int tmp;
        tmp = optimisedList.get(i);
        optimisedList.set(i, optimisedList.get(y));
        optimisedList.set(y, tmp);
    }

    /**
     * Obtiens la valeur de la liste optimisedList.
     * Exemple : optimisedList = [0,1,2], cela va faire ce calcul : matrix[0][1] + matrix[1][2]
     * @param matrix une matrice de FLOAT
     * @param optimisedList
     * @return
     */
    private float getOptimisedValue(Double[][] matrix, List<Integer> optimisedList) {

        float total = 0;
        for (int i = 0; i+1 < optimisedList.size(); i++){
            if (matrix[optimisedList.get(i)][optimisedList.get(i+1)] == 0){
                continue;
            }
            total += matrix[optimisedList.get(i)][optimisedList.get(i+1)];
        }
        return total;
    }

    /**
     * Obtiens la valeur de la liste optimisedList.
     * Exemple : optimisedList = [0,1,2], cela va faire ce calcul : matrix[0][1] + matrix[1][2]
     * @param matrix une matrice d'ENTIERS
     * @param optimisedList
     * @return
     */
    private int getOptimisedValue(Integer[][] matrix, List<Integer> optimisedList) {
        int total = 0;
        for (int i = 0; i+1 < optimisedList.size(); i++){
            if (matrix[optimisedList.get(i)][optimisedList.get(i+1)] == 0){
                continue;
            }
            total += matrix[optimisedList.get(i)][optimisedList.get(i+1)];
        }
        return total;
    }

    /**
     * construit une liste par défaut.
     * Exxemple : matrix.length = 3 -> la liste retournée sera [0,1,2]
     * @param matrix
     * @param <T>
     * @return
     */
    private <T> List<Integer> buildDefaultList(T[][] matrix) {
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < matrix.length; i++){
            res.add(i);
        }
        return res;
    }


    /**
     * Renvoie la matrice des distances enlevée du point "WareHouse" qui correspond à la dernière colonne de la matrice
     * @return
     */
    private Double[][] removeWareHouse_Double() {
        Double[][] distances = this.spots.getDistances();
        Double[][] tmp = new Double[distances.length-1][distances[0].length-1];
        for (int i = 0; i <= distances.length-2; i++){
            for (int y = 0; y <= distances.length-2; y++){
                tmp[i][y] = distances[i][y];
            }
        }
        return tmp;
    }

    /**
     * permet d'afficher une matrice toute zolie
     * @param list
     * @return
     */
    private String printList(List<List<Integer>> list){
        String s = "";
        for (List<Integer> subList: list) {
            s += subList.toString() +"\n";
        }
        return s;
    }

    //endregion


    public void run() {
        Double[][] distancesWithoutWare = removeWareHouse_Double();
        List<Integer> defaultList = buildDefaultList(distancesWithoutWare);

        List<Integer> list = null;
        try {
            list = neighbor_Distances_First_Pick((defaultList));
            list = neighbor_Distances_Best_Pick(defaultList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Best List : \n"+list.toString()+"\n Best Value : "+getOptimisedValue(this.spots.getDistances(), list));


        List<List<Integer>> matrixToSave = new ArrayList<>();
        matrixToSave.add(list);
        List<List<String>> strList = FileUtil.AddWarepointSpot(matrixToSave);
        FileUtil.WriteCompleteTravel(strList, "neighbor-"+ UUID.randomUUID() +".txt");
    }
}
