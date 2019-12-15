package com.example.info706.Model;

/**
 * Classe représentant un trait dans le canvas pour un mot rayé
 * @author Brozzoni Vincent / Jugand Théo
 */
public class Trait {
    public static final int TAILLE_TABLEAU = 4;
    /**
     * Couleur d'un trait
     */
    private int color;
    /**
     * Tableau de coordonées d'un trait dans le canvas
     */
    private float tabPoints[];

    /**
     * Constructeur de la classe Trait
     * @param color Couleur du trait
     */
    public Trait(int color){
        this.color = color;
        this.tabPoints = new float[TAILLE_TABLEAU];
    }

    /**
     * Méthode permettant de récupérer la couleur d'un trait
     * @return couleur du trait
     */
    public int getColor() {
        return color;
    }

    /**
     * Méthode permettant de récupérer le tableau de coordonnées d'un trait
     * @return Tableau de coordonnées du trait
     */
    public float[] getTabPoints() {
        return tabPoints;
    }

    /**
     * Méthode permettant d'assigner un tableau de coordonnées à un trait
     * @param tabPoints Tableau de coordonnées
     */
    public void setTabPoints(float[] tabPoints) {
        this.tabPoints = tabPoints;
    }
}
