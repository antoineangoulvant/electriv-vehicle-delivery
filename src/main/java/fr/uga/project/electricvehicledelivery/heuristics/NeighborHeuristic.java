package fr.uga.project.electricvehicledelivery.heuristics;

import fr.uga.project.electricvehicledelivery.domain.InstanceSpecifications;
import fr.uga.project.electricvehicledelivery.domain.Spots;
import fr.uga.project.electricvehicledelivery.utils.FileUtil;
import fr.uga.project.electricvehicledelivery.utils.SortUtil;

import java.util.*;

public class NeighborHeuristic implements IHeuristics {

    Spots spots;
    InstanceSpecifications instance;

    public NeighborHeuristic(InstanceSpecifications instance, Spots spots){
        this.instance = instance;
        this.spots = spots;
    }

    public Float[][] removeWareHouse_Float() {
        Float[][] distances = this.spots.getDistances();
        Float[][] tmp = new Float[distances.length-1][distances[0].length-1];
        for (int i = 0; i <= distances.length-2; i++){
            for (int y = 0; y <= distances.length-2; y++){
                tmp[i][y] = distances[i][y];
            }
        }
        return tmp;
    }

    //region NeighborHeuristic
    /**
     * Calcul des voisins optimaux à partir d'une liste de base.
     * La méthode calcule un nombre de voisins numberOfNeighborsWanted venant de la liste listToParse, qui améliorent
     * la valeur des distances ajoutées comparé à la liste de base.
     * Il effectue un simple swap de 2 valeurs dans la liste de base.
     * Exemple : si ma liste de base est [0,1,2] avec pour valeur des distances ajoutées 0.5,
     * et que l'on trouve un voisin [2,1,0] ayant pur valeur 0.2, alors ce dernier sera choisi.
     * Note : si jamais le nombre requis de voisins à renvoyer n'est pas atteint, on recommence la recherche avec le premier voisin,
     * jusqu'à numberOfRecursion boucles
     * trouvé.
     * @param numberOfNeighborsWanted
     * @param listToParse
     * @return
     */
    public List<List<Integer>> neighbor_Distances_Pick (int numberOfNeighborsWanted, List<Integer> listToParse, int numberOfRecursion) {
        int currentNumberNeighbor = 0;
        Float[][] distances = this.spots.getDistances();
        List<List<Integer>> neighbors = new ArrayList<>();
        //distances = removeWareHouse_Float(distances);
        List<Integer> currentList = listToParse == null ? buildDefaultList(distances) : listToParse;
        float defaultValue = getOptimisedValue(distances, currentList), pretendValue;

        for (int i = 0; i+1 < currentList.size() && currentNumberNeighbor < numberOfNeighborsWanted; i++){
            swap(i, i+1, currentList);

            pretendValue = getOptimisedValue(distances, currentList);

            System.out.println("Array : "+currentList.toString()+"\nPretendValue : "+pretendValue+"\n OptimisedValue : "+defaultValue+"\nmainIndex : "+i+"\n");

            if (pretendValue < defaultValue){
                System.out.println(" >> Adding list \n");

                neighbors.add(new ArrayList<>(currentList));
                currentNumberNeighbor++;
            }

            swap(i, i+1, currentList);

        }

        if (neighbors.size() == 0 && !listToParse.containsAll(currentList))
            neighbors.add(currentList);
        else if (neighbors.size() != 0 && numberOfRecursion <3 && neighbors.size() < numberOfNeighborsWanted){
            neighbors.addAll(neighbor_Distances_Pick(numberOfNeighborsWanted-neighbors.size(), neighbors.get(0), numberOfRecursion+1));
        }

        System.out.println("Neighbors : \n"+printList(neighbors)+"\n Recursion : "+numberOfRecursion);
        return neighbors;

    }

    public List<Integer> neighbor_Distances_First_Pick (List<Integer> listToParse) throws Exception {

        if (listToParse == null || listToParse.size() == 0){
            throw new Exception("ListToParse is null or Empty");
        }

        HashMap<List<Integer>, Float> map = new HashMap<>();

        Float[][] distances = this.spots.getDistances();

        List<Integer> firstList = getFirstNeighbor(listToParse);
        List<Integer> bestList = new ArrayList<>(firstList);

        while(firstList != null){
            map.put(new ArrayList<>(firstList), getOptimisedValue(distances, firstList));
            bestList = new ArrayList<>(firstList);
            firstList = getFirstNeighbor(firstList);

        }

        return bestList;
    }

    private List<Integer> getFirstNeighbor(List<Integer> currentList) {
        Float[][] distances = this.spots.getDistances();
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

    public HashMap<List<Integer>, Float> neighbor_Distances_Best_Pick (List<Integer> listToParse) throws Exception {

        if (listToParse == null || listToParse.size() == 0){
            throw new Exception("ListToParse is null or Empty");
        }

        HashMap<List<Integer>, Float> map = new HashMap<>();

        Float[][] distances = this.spots.getDistances();

        List<Integer> firstList = getFirstNeighbor(listToParse);

        while(firstList != null){
            map.put(new ArrayList<>(firstList), getOptimisedValue(distances, firstList));
            firstList = getFirstNeighbor(firstList);
        }

        return map;
    }


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

    private void swap(int i, int y, List<Integer> optimisedList) {
        int tmp;
        tmp = optimisedList.get(i);
        optimisedList.set(i, optimisedList.get(y));
        optimisedList.set(y, tmp);
    }

    /*
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
*/
    public float getOptimisedValue(Float[][] matrix, List<Integer> optimisedList) {

        float total = 0;
        for (int i = 0; i+1 < optimisedList.size(); i++){
            if (matrix[optimisedList.get(i)][optimisedList.get(i+1)] == 0){
                continue;
            }
            total += matrix[optimisedList.get(i)][optimisedList.get(i+1)];
        }
        return total;
    }

    public float getOptimisedValue(Integer[][] matrix, List<Integer> optimisedList) {
        float total = 0;
        for (int i = 0; i+1 < optimisedList.size(); i++){
            if (matrix[optimisedList.get(i)][optimisedList.get(i+1)] == 0){
                continue;
            }
            total += matrix[optimisedList.get(i)][optimisedList.get(i+1)];
        }
        return total;
    }


    public <T> List<Integer> buildDefaultList(T[][] matrix) {
        List<Integer> res = new ArrayList<>();
        //List<Integer> res = Arrays.asList(5,3,9,1,0,4,6,7,2,8);
        // Adding the warehouse at the beginning
        //res.add(matrix.length-1);
        for (int i = 0; i < matrix.length; i++){
            res.add(i);
        }

        return res;
    }

    public String printList(List<List<Integer>> list){
        String s = "";
        for (List<Integer> subList: list) {
            s += subList.toString() +"\n";
        }
        return s;
    }

    //endregion


    public void run() {
        SortUtil sort = new SortUtil();
        sort.setDistances(this.spots.getDistances());

        Float[][] distancesWithoutWare = removeWareHouse_Float();
        List<Integer> defaultList = buildDefaultList(distancesWithoutWare);

        List<Integer> list = null;
        try {
            list = neighbor_Distances_First_Pick(defaultList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Best List : \n"+list.toString()+"\n Best Value : "+getOptimisedValue(this.spots.getDistances(), list));

        //List<List<String>> strList = FileUtil.AddInitialWarepointSpot(list);
        //FileUtil.WriteCompleteTravel(strList, "neighbor-"+ UUID.randomUUID() +".txt");
    }
}
