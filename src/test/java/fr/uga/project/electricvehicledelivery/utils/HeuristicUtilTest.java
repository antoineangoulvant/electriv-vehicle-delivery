package fr.uga.project.electricvehicledelivery.utils;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

/**
 * Classe de test de la classe utilitaire de formatage
 * @author Antoine Angoulvant - Andréas Dedieu Meille
 */
public class HeuristicUtilTest {
    @Test
    public void formatTimeTest(){
        String expected = "0h 20m 34s";
        String toTest = HeuristicUtil.formatTime(1234);
        assertEquals(expected, toTest);
    }

    @Test
    public void swapDeliveryTest() {
        List<List<String>> expected = new ArrayList<>();
        expected.add(Stream.of("C","D").collect(Collectors.toList()));
        expected.add(Stream.of("A","B").collect(Collectors.toList()));

        List<List<String>> list = new ArrayList<>();
        list.add(Stream.of("A","B").collect(Collectors.toList()));
        list.add(Stream.of("C","D").collect(Collectors.toList()));

        List<List<String>> toTest = HeuristicUtil.swapDelivery(list,0,1);

        assertEquals(expected, toTest);
    }

    @Test
    public void buildDeliveryListTest() {
        List<String> expected = new ArrayList<>();
        expected.addAll(Stream.of("1","2","C","3","4").collect(Collectors.toList()));

        List<List<String>> list = new ArrayList<>();
        list.add(Stream.of("1","2").collect(Collectors.toList()));
        list.add(Stream.of("3","4").collect(Collectors.toList()));

        List<String> toTest = HeuristicUtil.buildDeliveryList(list);

        assertEquals(expected, toTest);
    }

    @Test
    public void swapElementDeliveryTest() {
        List<String> expected = new ArrayList<>();
        expected.addAll(Stream.of("3","2","C","1","4").collect(Collectors.toList()));

        List<String> list = new ArrayList<>();
        list.addAll(Stream.of("1","2","C","3","4").collect(Collectors.toList()));

        List<String> toTest = HeuristicUtil.swapFirstElementDelivery(list, 0,3);

        assertEquals(expected, toTest);
    }
}