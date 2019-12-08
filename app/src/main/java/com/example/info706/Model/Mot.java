package com.example.info706.Model;

public class Mot {

    private static final int TAILLE_MAX_MOT = 12;

    private String chaineMot;
    private int longueurMot;
    private Direction directionMot;
    private Sens sensMot;
    private String definition;

    public Mot(String chaine, int longueur , Direction direction , Sens sens, String definition){

        this.chaineMot = chaine;
        this.sensMot = sens;
        this.longueurMot = longueur;
        this.directionMot = direction;
        this.definition = definition;

    }

    public int getLongueurMot() {
        return longueurMot;
    }

    public Direction getDirectionMot() {
        return directionMot;
    }

    public Sens getSensMot() {
        return sensMot;
    }

    public String getChaineMot() {
        return chaineMot;
    }

    public String getDefinition(){ return definition; }

    public void setChaineMot(String chaineMot) {
        this.chaineMot = chaineMot;
    }

}