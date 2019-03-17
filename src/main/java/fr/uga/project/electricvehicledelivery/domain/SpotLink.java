package fr.uga.project.electricvehicledelivery.domain;

import java.util.ArrayList;

public class SpotLink<T> {

    public int customer1;
    public int customer2;
    public T value;

    public SpotLink(int customer1, int customer2, T value){
        this.customer1 = customer1;
        this.customer2 = customer2;
        this.value = value;
    }

    public static <T> SpotLink<T> DEFAULT(){
        return new SpotLink<T>(0, 0, null);
    }

}
