package com.example.info706.Model;

public class Mot {

    private String chaineMot;
    private int longueurMot;
    private Direction directionMot;
    private Sens sensMot;
    private String definition;
    private boolean trouve;
    private int couleur;

    public Mot(String chaine, int longueur , Direction direction , Sens sens, String definition , boolean trouve , int couleur){

        this.chaineMot = chaine;
        this.sensMot = sens;
        this.longueurMot = longueur;
        this.directionMot = direction;
        this.definition = definition;
        this.trouve = trouve;
        this.couleur = couleur;

    }

    public int getLongueurMot() {
        return this.longueurMot;
    }

    public Direction getDirectionMot() {
        return this.directionMot;
    }

    public Sens getSensMot() {
        return this.sensMot;
    }

    public String getChaineMot() {
        return this.chaineMot;
    }

    public String getDefinition(){ return this.definition; }

    public boolean getTrouve(){ return this.trouve;}

    public int getCouleur(){ return this.couleur;}

    public void setChaineMot(String chaineMot) {
        this.chaineMot = chaineMot;
    }

    public void setTrouve(boolean trouve){ this.trouve = trouve;}

    public void setCouleur(int couleur){ this.couleur = couleur;}
}