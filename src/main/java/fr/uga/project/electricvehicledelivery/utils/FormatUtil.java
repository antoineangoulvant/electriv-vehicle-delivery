package fr.uga.project.electricvehicledelivery.utils;

/**
 * Classe contenant des méthodes de formatages communes
 */
public class FormatUtil {
    /**
     * Méthode permettant de formatter un temps en secondes sous un format plus simple à lire
     * @param seconds temps en seconde
     * @return temps formatté
     */
    public static String formatTime(int seconds){
        int hours = seconds / 3600;
        int minutes = (seconds / 60)-(hours*60);
        int secondsLeft = seconds - (hours*3600) - (minutes*60);
        return hours + "h " + minutes + "m " + secondsLeft + "s";
    }
}
