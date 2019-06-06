package fr.uga.project.electricvehicledelivery.utils;

import fr.uga.project.electricvehicledelivery.domain.Spot;
import fr.uga.project.electricvehicledelivery.domain.Spots;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Classe de test de la classe utilitaire de formatage
 * @author Antoine Angoulvant - Andréas Dedieu Meille
 */
public class FileUtilTest {
    @Test
    public void AddWarehouseSpot_Valid(){
        List<List<Integer>> parameters = Arrays.asList(Arrays.asList(0,1,2,3), Arrays.asList(1,0,2,3));

        List<List<String>> toTest = FileUtil.AddWarepointSpot(parameters);
        List<List<String>> expected = Arrays.asList(Arrays.asList("C", "0","1","2", "R"), Arrays.asList("C", "1","0","2", "R"));
        Assert.assertEquals(expected, toTest);
    }

    @Test(expected = IllegalArgumentException.class)
    public void AddWarehouseSpot_ParameterHigherThanArraySize(){
        List<List<Integer>> parameters = Arrays.asList(Arrays.asList(0,1,2,4));
        FileUtil.AddWarepointSpot(parameters);
    }

    @Test(expected = IllegalArgumentException.class)
    public void AddWarehouse_NullParameter(){
        List<List<Integer>> empty = null;
        FileUtil.AddWarepointSpot(empty);
    }

    @Test
    public void AddWareHouseSpot_EmptyParameter(){
        List<List<Integer>> empty = new ArrayList<>();
        List<List<String>> emptyRes = FileUtil.AddWarepointSpot(empty);

        assertEquals(new ArrayList<>(), emptyRes);
    }

    @Test
    public void CheckWritingFile(){

        int beforeWriting = new File(Constants.RESULTS_FOLDER_PATH).listFiles().length;

        List<List<String>> parameter = Arrays.asList(Arrays.asList("C", "0", "1", "2", "R"), Arrays.asList("C", "1", "0", "2", "R"));
        FileUtil.WriteCompleteTravel(parameter, "test.txt");

        int afterWriting = new File(Constants.RESULTS_FOLDER_PATH).listFiles().length;

        assertEquals(beforeWriting+1, afterWriting);

        try {
            Files.deleteIfExists(Paths.get(Constants.RESULTS_FOLDER_PATH+"test.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void AddRechargeStops_Valid(){
        Spots spots = new Spots(Constants.Assets.ASSET_LYON_40_2_2.getInstance());
        List<Integer> parameter = Arrays.asList(0,1,2,3,4,6,5,7,8,9,10);
        List<Integer> res = SpotUtil.AddRechargeStops(spots.getDistances(), parameter, 10);
        List<Integer> expected = Arrays.asList(0,1,2,11,3,4,11,6,5,7,11,8,9,10);
        assertEquals(expected, res);

    }

    @Test
    public void AddMultipleDays_Valid(){
        Spots spots = new Spots(Constants.Assets.ASSET_LYON_40_2_2.getInstance());
        List<Integer> parameter = Arrays.asList(0,1,2,3,4,6,5,7,8,9,10);
        List<Integer> res = SpotUtil.AddMultipleDays(spots.getTimes(), parameter, 2000);
        List<Integer> expected = Arrays.asList(0,1,2,3,4,6,11,5,7,8,9,10);
        assertEquals(expected, res);

    }
}