package com.example.info706.Model;

import java.util.ArrayList;
import java.util.Random;

public class Grille {

    private static final int TAILLE_DEFAUT = 10 ;
    private static final String CARACTERE_DEFAUT = " ";
    private static final int NOMBRE_MOTS_PARTIE = 3;
    private String[][] grilleCaracteres;
    private ArrayList<Mot> listeMots;
    private ArrayList<Mot> listeMotsFinale;


    public Grille(String[] mots){

        this.listeMots = new ArrayList<>();
        for (int i = 0 ; i < mots.length ; i++){
            Mot temp = new Mot(mots[i],mots[i].length(),Direction.randomDirection(), Sens.randomSens());
            this.listeMots.add(temp);
        }
        this.inititialiseGrille();
        this.genererGrille();
    }


    public void inititialiseGrille (){
        this.grilleCaracteres = new String[TAILLE_DEFAUT][TAILLE_DEFAUT];
        int i,j;
        for (i = 0 ; i < TAILLE_DEFAUT ; i++){
            for(j = 0 ; j < TAILLE_DEFAUT; j++){
                this.grilleCaracteres[i][j] = CARACTERE_DEFAUT;
            }
        }
    }

    public void genererGrille(){
        this.listeMotsFinale = new ArrayList<>();
        int i = 0;

        while(this.listeMotsFinale.size()< NOMBRE_MOTS_PARTIE && i< this.listeMots.size()){

                Mot motAPlacer = this.listeMots.get(i);

                switch(motAPlacer.getDirectionMot()){
                    case HORIZONTAL:

                        if (motAPlacer.getSensMot()== Sens.DROIT) {
                            tentativePlacerMotHorizontalDroit(motAPlacer);
                        }else if(motAPlacer.getSensMot()== Sens.INVERSE){
                            tentativePlacerMotHorizontalInverse(motAPlacer);
                        }

                        break;
                    case VERTICAL:

                        if (motAPlacer.getSensMot()== Sens.DROIT) {
                            tentativePlacerMotVerticalDroit(motAPlacer);
                        }else if(motAPlacer.getSensMot()== Sens.INVERSE){
                            tentativePlacerMotVerticalInverse(motAPlacer);
                        }

                        break;
                    default:
                        break;
                }
        }
        remplirCasesRestantes();
    }


    //*********************************************************************************************
    //*********************************************************************************************
    //Methodes tentant de placer les mots dans la grille

    private void tentativePlacerMotHorizontalDroit(Mot motAPlacer) {
        int colonneDebutMot,ligneMot,timeOut;

        colonneDebutMot = new Random().nextInt(TAILLE_DEFAUT - motAPlacer.getLongueurMot() + 1);
        ligneMot = new Random().nextInt(TAILLE_DEFAUT);
        timeOut = 0;

        while (!positionValideHorizontalDroit(motAPlacer, colonneDebutMot, ligneMot) && timeOut < 10) {
            colonneDebutMot = new Random().nextInt(TAILLE_DEFAUT - motAPlacer.getLongueurMot() + 1);
            ligneMot = new Random().nextInt(TAILLE_DEFAUT);
            timeOut++;
        }
        if (timeOut < 10) {
            placerMotHorizontalDroit(motAPlacer, colonneDebutMot, ligneMot);
            this.listeMotsFinale.add(motAPlacer);
        }
    }

    private void tentativePlacerMotHorizontalInverse(Mot motAPlacer) {
        int colonneDebutMot,ligneMot,timeOut;

        colonneDebutMot = new Random().nextInt(TAILLE_DEFAUT - motAPlacer.getLongueurMot()) + motAPlacer.getLongueurMot();
        ligneMot = new Random().nextInt(TAILLE_DEFAUT);
        timeOut = 0;

        while (!positionValideHorizontalInverse(motAPlacer, colonneDebutMot, ligneMot) && timeOut < 10) {
            colonneDebutMot = new Random().nextInt(TAILLE_DEFAUT - motAPlacer.getLongueurMot()) + motAPlacer.getLongueurMot();
            ligneMot = new Random().nextInt(TAILLE_DEFAUT);
            timeOut++;
        }
        if (timeOut < 10) {
            placerMotHorizontalInverse(motAPlacer, colonneDebutMot, ligneMot);
            this.listeMotsFinale.add(motAPlacer);
        }
    }


    private void tentativePlacerMotVerticalDroit(Mot motAPlacer) {

        int ligneDebutMot,colonneMot,timeOut;

        ligneDebutMot = new Random().nextInt(TAILLE_DEFAUT - motAPlacer.getLongueurMot() + 1);
        colonneMot = new Random().nextInt(TAILLE_DEFAUT);
        timeOut = 0;
        while(!positionValideVerticalDroit(motAPlacer,colonneMot,ligneDebutMot) && timeOut < 10){
            ligneDebutMot = new Random().nextInt(TAILLE_DEFAUT - motAPlacer.getLongueurMot() + 1);
            colonneMot = new Random().nextInt(TAILLE_DEFAUT);
            timeOut++;
        }
        if(timeOut < 10) {
            placerMotVerticalDroit(motAPlacer, colonneMot, ligneDebutMot);
            this.listeMotsFinale.add(motAPlacer);
        }
    }

    private void tentativePlacerMotVerticalInverse(Mot motAPlacer) {
        int ligneDebutMot,colonneMot,timeOut;

        ligneDebutMot = new Random().nextInt(TAILLE_DEFAUT - motAPlacer.getLongueurMot() )+motAPlacer.getLongueurMot();
        colonneMot = new Random().nextInt(TAILLE_DEFAUT);
        timeOut = 0;
        while(!positionValideVerticalInverse(motAPlacer,colonneMot,ligneDebutMot) && timeOut < 10){
            ligneDebutMot = new Random().nextInt(TAILLE_DEFAUT - motAPlacer.getLongueurMot() )+ motAPlacer.getLongueurMot();
            colonneMot = new Random().nextInt(TAILLE_DEFAUT);
            timeOut++;
        }
        if(timeOut < 10) {
            placerMotVerticalInverse(motAPlacer, colonneMot, ligneDebutMot);
            this.listeMotsFinale.add(motAPlacer);
        }
    }



    //************************************************************************
    //************************************************************************
    //Methodes testant la validite de la position des mots que l'on veut placer

    private boolean positionValideHorizontalDroit(Mot motAPlacer, int colonneDebutMot, int ligneMot) {
        boolean placeLibre = true;
        int i = 0;
        int x = colonneDebutMot;
        while (i < motAPlacer.getLongueurMot() && placeLibre){
            String lettre = String.valueOf(motAPlacer.getChaineMot().charAt(i));
            if (!this.grilleCaracteres[x][ligneMot].equals(CARACTERE_DEFAUT) && !lettre.equals(this.grilleCaracteres[x][ligneMot])){
                placeLibre = false;
            }
            i++;
            x++;
        }
        return placeLibre;
    }

    private boolean positionValideHorizontalInverse(Mot motAPlacer, int colonneDebutMot, int ligneMot) {
        boolean placeLibre = true;
        int i = 0;
        int x = colonneDebutMot;
        while (i < motAPlacer.getLongueurMot() && placeLibre){
            String lettre = String.valueOf(motAPlacer.getChaineMot().charAt(i));
            if (!this.grilleCaracteres[x][ligneMot].equals(CARACTERE_DEFAUT) && !lettre.equals(this.grilleCaracteres[x][ligneMot])){
                placeLibre = false;
            }
            i++;
            x--;
        }
        return placeLibre;
    }

    private boolean positionValideVerticalDroit(Mot motAPlacer, int colonneMot, int ligneDebutMot) {
        boolean placeLibre = true;
        int i = 0;
        int y = ligneDebutMot;
        while (i < motAPlacer.getLongueurMot() && placeLibre){
            String lettre = String.valueOf(motAPlacer.getChaineMot().charAt(i));
            if (!this.grilleCaracteres[colonneMot][y].equals(CARACTERE_DEFAUT) && !lettre.equals(this.grilleCaracteres[colonneMot][y])){
                placeLibre = false;
            }
            i++;
            y++;
        }

        return placeLibre;
    }

    private boolean positionValideVerticalInverse(Mot motAPlacer, int colonneMot, int ligneDebutMot) {
        boolean placeLibre = true;
        int i = 0;
        int y = ligneDebutMot;
        while (i < motAPlacer.getLongueurMot() && placeLibre){
            String lettre = String.valueOf(motAPlacer.getChaineMot().charAt(i));
            if (!this.grilleCaracteres[colonneMot][y].equals(CARACTERE_DEFAUT) && !lettre.equals(this.grilleCaracteres[colonneMot][y])){
                placeLibre = false;
            }
            i++;
            y--;
        }

        return placeLibre;
    }
    //*************************************************************************************
    //*************************************************************************************
    //Methodes de placement des mots dans la grille


    private void placerMotHorizontalDroit(Mot motAPlacer, int colonneDebutMot, int ligneMot) {
        int x = colonneDebutMot;
        for (int i = 0 ; i < motAPlacer.getLongueurMot() ; i++){
            String lettre = String.valueOf(motAPlacer.getChaineMot().charAt(i));
            this.grilleCaracteres[x][ligneMot] = lettre;
            x++;

        }
    }

    private void placerMotHorizontalInverse(Mot motAPlacer, int colonneDebutMot, int ligneMot) {
        int x = colonneDebutMot;
        for (int i = 0 ; i < motAPlacer.getLongueurMot() ; i++){
            String lettre = String.valueOf(motAPlacer.getChaineMot().charAt(i));
            this.grilleCaracteres[x][ligneMot] = lettre;
            x--;
        }
    }

    private void placerMotVerticalDroit(Mot motAPlacer, int colonneMot, int ligneDebutMot) {
        int y = ligneDebutMot;
        for (int i = 0 ; i < motAPlacer.getLongueurMot() ; i++){
            String lettre = String.valueOf(motAPlacer.getChaineMot().charAt(i));
            this.grilleCaracteres[colonneMot][y] = lettre;
            y++;
        }
    }

    private void placerMotVerticalInverse(Mot motAPlacer, int colonneMot, int ligneDebutMot) {
        int y = ligneDebutMot;
        for (int i = 0 ; i < motAPlacer.getLongueurMot() ; i++){
            String lettre = String.valueOf(motAPlacer.getChaineMot().charAt(i));
            this.grilleCaracteres[colonneMot][y] = lettre;
            y--;
        }
    }


    //***************************************************************************************
    //***************************************************************************************
    //Methode permattant de remplir les cases restantes de la grille

    public void remplirCasesRestantes(){
        for(int i = 0 ; i < TAILLE_DEFAUT ; i++){
            for(int j = 0 ; j< TAILLE_DEFAUT ; j++){
                if(this.grilleCaracteres[i][j].equals(CARACTERE_DEFAUT)){
                    char c = (char) (new Random().nextInt(26) + 'a');
                    this.grilleCaracteres[i][j] = String.valueOf(c);
                }
            }
        }
    }


    public static int getTailleDefaut() {
        return TAILLE_DEFAUT;
    }

    public String[][] getGrilleCaracteres() {
        return grilleCaracteres;
    }

    public ArrayList<Mot> getListeMotsFinale() {
        return listeMotsFinale;
    }
}
