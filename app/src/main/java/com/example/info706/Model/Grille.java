package com.example.info706.Model;

import java.util.ArrayList;

public class Grille {

    private static final int TAILLE_DEFAUT = 10 ;
    private String[][] grilleMots;
    private ArrayList<Mot> listeMots;

    public Grille(){
        this.listeMots = new ArrayList<>();
        this.inititialiseGrille();

    }

    public void inititialiseGrille (){
        this.grilleMots = new String[TAILLE_DEFAUT][TAILLE_DEFAUT];
        int i,j;
        for (i = 0 ; i < TAILLE_DEFAUT ; i++){
            for(j = 0 ; j < TAILLE_DEFAUT; j++){
                this.grilleMots[i][j] = "B";
            }
        }
    }

    public static int getTailleDefaut() {
        return TAILLE_DEFAUT;
    }

    public String[][] getGrilleMots() {
        return grilleMots;
    }

    public void setGrilleMots(String[][] grilleMots) {
        this.grilleMots = grilleMots;
    }

    public ArrayList<Mot> getListeMots() {
        return listeMots;
    }

    public void setListeMots(ArrayList<Mot> listeMots) {
        this.listeMots = listeMots;
    }

}
