package com.example.info706.Model;

import java.util.ArrayList;

public class Grille {

    private static final int TAILLE_DEFAUT = 10 ;
    private String[][] grilleMots;
    private ArrayList<Mot> listeMots;

    public Grille(ArrayList<Mot> listeMots){
        this.listeMots = listeMots;
        this.inititialiseGrille();

    }

    public void inititialiseGrille (){
        int i,j;
        for (i = 0 ; i < TAILLE_DEFAUT ; i++){
            for(j = 0 ; j < TAILLE_DEFAUT; j++){
                this.grilleMots[i][j] = "!";
            }
        }
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
