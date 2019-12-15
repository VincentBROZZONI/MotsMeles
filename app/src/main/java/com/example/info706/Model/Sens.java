package com.example.info706.Model;

import java.util.Random;

/**
 * Enumeration représentant les différents sens d'un mot dans la grille
 * @author Brozzoni Vincent / Jugand Théo
 */
public enum Sens {
    DROIT,
    INVERSE;

    /**
     * Méthode permettant de générer un sens aléatoire pour un mot
     * @return
     */
    public static Sens randomSens(){
        return values()[new Random().nextInt(values().length)];
    }
}
