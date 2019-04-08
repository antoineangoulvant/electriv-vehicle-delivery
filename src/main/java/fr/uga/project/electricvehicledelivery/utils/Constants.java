package fr.uga.project.electricvehicledelivery.utils;

/**
 * Classe contenant toutes les constantes utiles dans l'application
 * Réalisé par Antoine Angoulvant et Andréas Dedieu Meille
 */
public class Constants {
    public enum Assets{
        ASSET_LYON0("lyon0"),
        ASSET_LYON1("lyon1"),
        ASSET_LYON_40_1_1("lyon_40_1_1"),
        ASSET_LYON_40_1_2("lyon_40_1_2"),
        ASSET_LYON_40_1_3("lyon_40_1_3"),
        ASSET_LYON_40_2_1("lyon_40_2_1"),
        ASSET_LYON_40_2_2("lyon_40_2_2"),
        ASSET_LYON_40_2_3("lyon_40_2_3"),
        ASSET_LYON_100_1_1("lyon_100_1_1"),
        ASSET_LYON_100_1_2("lyon_100_1_2"),
        ASSET_LYON_100_1_3("lyon_100_1_3"),
        ASSET_LYON_100_2_1("lyon_100_2_1"),
        ASSET_LYON_100_2_2("lyon_100_2_2"),
        ASSET_LYON_100_2_3("lyon_100_2_3"),
        ASSET_LYON_100_3_1("lyon_100_3_1"),
        ASSET_LYON_100_3_2("lyon_100_3_2"),
        ASSET_LYON_100_3_3("lyon_100_3_3"),
        ASSET_LYON_150_1_1("lyon_150_1_1"),
        ASSET_LYON_150_1_2("lyon_150_1_2"),
        ASSET_LYON_150_1_3("lyon_150_1_3"),
        ASSET_LYON_150_2_1("lyon_150_2_1"),
        ASSET_LYON_150_2_2("lyon_150_2_2"),
        ASSET_LYON_150_2_3("lyon_150_2_3"),
        ASSET_LYON_200_1_1("lyon_200_1_1"),
        ASSET_LYON_200_1_2("lyon_200_1_2"),
        ASSET_LYON_200_1_3("lyon_200_1_3"),
        ASSET_LYON_200_2_1("lyon_200_2_1"),
        ASSET_LYON_200_2_2("lyon_200_2_2"),
        ASSET_LYON_200_2_3("lyon_200_2_3");

        private final String PATH = "src/main/resources/instances/";
        private String instance;

        Assets(String instance) {
            this.instance = instance;
        }

        public String getInstance(){
            return PATH + this.instance;
        }
    }

    public static final int DISTANCE_WEIGHT = 1;
    public static final int DELIVERY_LENGTH_WEIGHT = 1;
    public static final int CAPACITY_WEIGHT = 1;
}
