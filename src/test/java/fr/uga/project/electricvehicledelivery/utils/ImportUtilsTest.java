package fr.uga.project.electricvehicledelivery.utils;

import fr.uga.project.electricvehicledelivery.domain.InstanceSpecifications;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Classe de test de la classe utilitaire d'import
 */
public class ImportUtilsTest {
    /** Chemin d'accès au fichier de test */
    private String PATH = "src/test/resources/instances/lyon0";

    @Test
    public void vehicleParse() {
        InstanceSpecifications expected = new InstanceSpecifications();
        expected.setMaxDist(250);
        expected.setCapacity(100);
        expected.setChargeSlow(480);
        expected.setChargeMedium(180);
        expected.setChargeFast(60);
        expected.setStartTime("07:00");
        expected.setEndTime("19:00");

        InstanceSpecifications toTest = ImportUtils.vehicleParse(PATH + "/vehicle.ini");
        assertEquals(expected,toTest);
    }

    @Test
    public void coordsParse() {
        Float[][] expected = {{},{}};

        Float[][] toTest = ImportUtils.coordsParse(PATH);

        assertArrayEquals(expected, toTest);
    }

    @Test
    public void distancesParse() {
        System.out.println(ImportUtils.distancesParse(PATH));
    }

    @Test
    public void timesParse() {
        System.out.println(ImportUtils.timesParse(PATH));
    }

    @Test
    public void demandsParse() {
        System.out.println(ImportUtils.demandsParse(PATH));
    }
}