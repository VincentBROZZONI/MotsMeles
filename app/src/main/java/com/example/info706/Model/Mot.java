package com.example.info706.Model;

public class Mot {

    private static final int TAILLE_MAX_MOT = 10;



    private String chaineMot;
    private int longueurMot;
    private Direction directionMot;
    private Sens sensMot;

    public Mot(String chaine, int longueur , Direction direction , Sens sens){

        this.chaineMot = chaine;
        this.sensMot = sens;
        this.longueurMot = longueur;
        this.directionMot = direction;

    }

    public int getLongueurMot() {
        return longueurMot;
    }

    public void setLongueurMot(int longueurMot) {
        this.longueurMot = longueurMot;
    }

    public Direction getDirectionMot() {
        return directionMot;
    }

    public void setDirectionMot(Direction directionMot) {
        this.directionMot = directionMot;
    }

    public Sens getSensMot() {
        return sensMot;
    }

    public void setSensMot(Sens sensMot) {
        this.sensMot = sensMot;
    }

    public String getChaineMot() {
        return chaineMot;
    }

    public void setChaineMot(String chaineMot) {
        this.chaineMot = chaineMot;
    }

}
