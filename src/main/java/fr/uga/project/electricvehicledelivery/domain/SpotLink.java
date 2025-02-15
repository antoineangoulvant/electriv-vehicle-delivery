package fr.uga.project.electricvehicledelivery.domain;

/**
 * @author Antoine Angoulvant - Andréas Dedieu Meille
 * @param <T>
 */
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
        return new SpotLink<>(0, 0, null);
    }
}
