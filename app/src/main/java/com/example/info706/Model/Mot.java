package com.example.info706.Model;

/**
 * Classe représentant un mot
 */
public class Mot {

    /**
     * La chaine de caractère du mot
     */
    private String chaineMot;

    /**
     * La longueur de la chaine de caractère du mot
     */
    private int longueurMot;

    /**
     * La direction dans laquelle va le mot
     */
    private Direction directionMot;

    /**
     * Le sens dans lequel le mot est tourné
     */
    private Sens sensMot;

    /**
     * La définition correspondant au mot
     */
    private String definition;

    /**
     * Booléen indiquant si le mot a été trouvé dans la grille
     */
    private boolean trouve;

    /**
     * La couleur dans laquelle coloré le mot dans la listView
     */
    private int couleur;

    /**
     * Constructeur d'un mot
     * @param chaine
     * @param longueur
     * @param direction
     * @param sens
     * @param definition
     * @param trouve
     * @param couleur
     */
    public Mot(String chaine, int longueur , Direction direction , Sens sens, String definition , boolean trouve , int couleur){
        this.chaineMot = chaine;
        this.sensMot = sens;
        this.longueurMot = longueur;
        this.directionMot = direction;
        this.definition = definition;
        this.trouve = trouve;
        this.couleur = couleur;
    }

    /**
     * Constructeur vide d'un mot
     */
    public Mot(){
    }

    /**
     * Getter de la longueur du mot
     * @return
     * un entier
     */
    public int getLongueurMot() {
        return this.longueurMot;
    }

    /**
     * Getter de la direction du mot
     * @return
     * la direction issue de l'enumeration Direction
     */
    public Direction getDirectionMot() {
        return this.directionMot;
    }

    /**
     * Getter du sens du mot
     * @return
     * le sens issue de l'enumeration Sens
     */
    public Sens getSensMot() {
        return this.sensMot;
    }

    /**
     * Getter de la chaine de caractere du mot
     * @return
     * une chaine de caractere correspondant au mot
     */
    public String getChaineMot() {
        return this.chaineMot;
    }

    /**
     * Getter de la definition
     * @return
     * une chaine de caractere correspondant a la definition
     */
    public String getDefinition(){ return this.definition; }

    /**
     * Getter du booleen indiquant si le mot est trouvé
     * @return
     */
    public boolean getTrouve(){ return this.trouve;}

    /**
     * Getter de la couleur du mot dans la listView
     * @return
     * un entier coorespondant a la valeur de la couleur
     */
    public int getCouleur(){ return this.couleur;}

    /**
     * Setter de la cahine de caractere du mot
     * @param chaineMot
     * la chaine de caractere que l'on attribue au mot
     */
    public void setChaineMot(String chaineMot) {
        this.chaineMot = chaineMot;
    }

    /**
     * Setter du booleen indiquant si le mot est trouvé
     * @param trouve
     * un booleen
     */
    public void setTrouve(boolean trouve){ this.trouve = trouve;}

    /**
     * Setter de la couleur du mot dans la listView
     * @param couleur
     * un entier coorespondant a la valeur de la couleur
     */
    public void setCouleur(int couleur){ this.couleur = couleur;}
}