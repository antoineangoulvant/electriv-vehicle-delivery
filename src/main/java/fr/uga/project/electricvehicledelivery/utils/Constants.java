package fr.uga.project.electricvehicledelivery.utils;

/**
 * Classe contenant toutes les constantes utiles dans l'application
 * @author Antoine Angoulvant - Andréas Dedieu Meille
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

        /** Méthode retournant l'instance avec le chemin d'accès */
        public String getInstance(){
            return PATH + this.instance;
        }
    }

    /** Constante représentant un chargement de camion dans la solution */
    public static final String TRUCK_LOADING = "C";

    /** Constante représentant un cargement de batterie dans la solution */
    public static final String BATTERY_LOADING = "R";

    /** Chemin d'accès pour les résultats */
    static final String RESULTS_FOLDER_PATH = "src/main/resources/Results/";
}
