package fr.uga.project.electricvehicledelivery.utils;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Classe de test de la classe utilitaire de formatage
 * @author Antoine Angoulvant - Andréas Dedieu Meille
 */
public class FormatUtilTest {
    @Test
    public void formatTimeTest(){
        String expected = "0h 20m 34s";
        String toTest = FormatUtil.formatTime(1234);
        assertEquals(expected, toTest);
    }
}