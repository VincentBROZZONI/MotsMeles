package com.example.info706.Model;

import java.util.Random;

/**
 * Enumeration représentant les différentes directions d'un mot dans la grille
 * @author Brozzoni Vincent / Jugand Théo
 */
public enum Direction {
    HORIZONTAL,
    VERTICAL,
    DIAGONAL_BAS,
    DIAGONAL_HAUT
    ;

    /**
     * Méthode permettant de récupérer une direction aléatoire
     * @return
     */
    public static Direction randomDirection(){
        return values()[new Random().nextInt(values().length)];
    }
}