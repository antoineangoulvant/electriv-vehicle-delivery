package fr.uga.project.electricvehicledelivery.utils;

import fr.uga.project.electricvehicledelivery.domain.InstanceSpecifications;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Classe de test de la classe utilitaire d'import
 * @author Antoine Angoulvant - Andréas Dedieu Meille
 */
public class ImportUtilsTest {
    /**
     * Chemin d'accès au fichier de test
     */
    private String PATH = "src/test/resources/instances/lyon0";

    @Test
    public void vehicleParseTest() {
        InstanceSpecifications expected = new InstanceSpecifications();
        expected.setMaxDist(250);
        expected.setCapacity(100);
        expected.setChargeSlow(480);
        expected.setChargeMedium(180);
        expected.setChargeFast(60);
        expected.setStartTime("07:00");
        expected.setEndTime("19:00");

        InstanceSpecifications toTest = ImportUtils.vehicleParse(PATH + "/vehicle.ini");
        assertEquals(expected, toTest);
        assertEquals(25200, toTest.getStartTimeSeconds());
        assertEquals(68400, toTest.getEndTimeToSeconds());
    }

    @Test
    public void coordsParseTest() {
        Double[][] expected = {
                {45.7270459, 4.8653198},
                {45.7279015, 4.8697412},
                {45.7238159, 4.8694674},
                {45.7237796, 4.8609922},
                {45.725869, 4.8620625},
                {45.7268434, 4.8604954},
                {45.7288445, 4.8598189},
                {45.7195966, 4.8636256},
                {45.7246533, 4.8658674},
                {45.7250825, 4.8639278},
                {45.7165463, 4.8596417}
        };

        Double[][] toTest = ImportUtils.coordsParse(PATH);
        assertArrayEquals(expected, toTest);
    }

    @Test
    public void distancesParseTest() {
        Double[][] expected = {
                {0.000, 0.356, 0.482, 0.495, 0.285, 0.375, 0.472, 0.839, 0.269, 0.244, 1.248},
                {0.356, 0.000, 0.455, 0.819, 0.637, 0.727, 0.777, 1.038, 0.470, 0.549, 1.486},
                {0.482, 0.455, 0.000, 0.658, 0.618, 0.774, 0.935, 0.653, 0.295, 0.452, 1.111},
                {0.495, 0.819, 0.658, 0.000, 0.247, 0.343, 0.571, 0.508, 0.391, 0.270, 0.811},
                {0.285, 0.637, 0.618, 0.247, 0.000, 0.163, 0.374, 0.708, 0.325, 0.169, 1.054},
                {0.375, 0.727, 0.774, 0.343, 0.163, 0.000, 0.229, 0.842, 0.483, 0.331, 1.147},
                {0.472, 0.777, 0.935, 0.571, 0.374, 0.229, 0.000, 1.070, 0.662, 0.526, 1.368},
                {0.839, 1.038, 0.653, 0.508, 0.708, 0.842, 1.070, 0.000, 0.589, 0.610, 0.459},
                {0.269, 0.470, 0.295, 0.391, 0.325, 0.483, 0.662, 0.589, 0.000, 0.158, 1.023},
                {0.244, 0.549, 0.452, 0.270, 0.169, 0.331, 0.526, 0.610, 0.158, 0.000, 1.006},
                {1.248, 1.486, 1.111, 0.811, 1.054, 1.147, 1.368, 0.459, 1.023, 1.006, 0.000}
        };

        Double[][] toTest = ImportUtils.distancesParse(PATH);
        assertArrayEquals(expected, toTest);
    }

    @Test
    public void timesParseTest() {
        Integer[][] expected = {
                {0, 42, 57, 59, 34, 45, 56, 100, 32, 29, 149},
                {42, 0, 54, 98, 76, 87, 93, 124, 56, 65, 178},
                {57, 54, 0, 78, 74, 92, 112, 78, 35, 54, 133},
                {59, 98, 78, 0, 29, 41, 68, 60, 46, 32, 97},
                {34, 76, 74, 29, 0, 19, 44, 84, 39, 20, 126},
                {45, 87, 92, 41, 19, 0, 27, 101, 57, 39, 137},
                {56, 93, 112, 68, 44, 27, 0, 128, 79, 63, 164},
                {100, 124, 78, 60, 84, 101, 128, 0, 70, 73, 55},
                {32, 56, 35, 46, 39, 57, 79, 70, 0, 18, 122},
                {29, 65, 54, 32, 20, 39, 63, 73, 18, 0, 120},
                {149, 178, 133, 97, 126, 137, 164, 55, 122, 120, 0}
        };

        Integer[][] toTest = ImportUtils.timesParse(PATH);
        assertArrayEquals(expected, toTest);
    }

    @Test
    public void demandsParseTest() {
        Integer[] expected = {18, 21, 23, 13, 17, 6, 6, 19, 8, 16};

        Integer[] toTest = ImportUtils.demandsParse(PATH);
        assertArrayEquals(expected, toTest);
    }
}