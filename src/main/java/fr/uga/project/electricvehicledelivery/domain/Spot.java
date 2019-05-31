package fr.uga.project.electricvehicledelivery.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe représentant un point avec ses coordonnées et son id
 * @author Antoine Angoulvant - Andréas Dedieu Meille
 */
@Data
public class Spot {
    /** Id du point */
    private int id;
    /** Coordonnée x du point */
    private double x;
    /** Coordonnée y du point */
    private double y;
}
