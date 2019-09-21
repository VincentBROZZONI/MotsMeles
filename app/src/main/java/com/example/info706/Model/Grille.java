package com.example.info706.Model;

import java.util.ArrayList;
import java.util.Random;

public class Grille {

    private static final int TAILLE_DEFAUT = 10 ;
    private static final String CARACTERE_DEFAUT = " ";
    private static final int NOMBRE_MOTS_PARTIE = 12 ;
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
                        tentativePlacerMotHorizontal(motAPlacer);
                        break;
                    case VERTICAL:
                        tentativePlacerMotVertical(motAPlacer);
                        break;
                    case DIAGONAL_BAS:
                        tentativePlacerMotDiagonalBas(motAPlacer);
                        break;
                    case DIAGONAL_HAUT:
                        tentativePlacerMotDiagonalHaut(motAPlacer);
                        break;
                    default:
                        break;
                }
                i++;
        }
        remplirCasesRestantes();
    }


    //*********************************************************************************************
    //*********************************************************************************************
    //Methodes tentant de placer les mots dans la grille

    private void tentativePlacerMotHorizontal(Mot motAPlacer) {
        int colonneDebutMot,ligneMot,timeOut;

        if (motAPlacer.getSensMot() == Sens.INVERSE){
            inverseMot(motAPlacer);
        }
        colonneDebutMot = new Random().nextInt(TAILLE_DEFAUT - motAPlacer.getLongueurMot() + 1);
        ligneMot = new Random().nextInt(TAILLE_DEFAUT);
        timeOut = 0;

        while (!positionValideHorizontal(motAPlacer, colonneDebutMot, ligneMot) && timeOut < 10) {
            colonneDebutMot = new Random().nextInt(TAILLE_DEFAUT - motAPlacer.getLongueurMot() + 1);
            ligneMot = new Random().nextInt(TAILLE_DEFAUT);
            timeOut++;
        }
        if (timeOut < 10) {
            placerMotHorizontal(motAPlacer, colonneDebutMot, ligneMot);
            if (motAPlacer.getSensMot() == Sens.INVERSE){
                inverseMot(motAPlacer);
            }
            this.listeMotsFinale.add(motAPlacer);
        }
    }

    private void tentativePlacerMotVertical(Mot motAPlacer) {

        int ligneDebutMot, colonneMot, timeOut;

        if (motAPlacer.getSensMot() == Sens.INVERSE) {
            inverseMot(motAPlacer);
        }

        ligneDebutMot = new Random().nextInt(TAILLE_DEFAUT - motAPlacer.getLongueurMot() + 1);
        colonneMot = new Random().nextInt(TAILLE_DEFAUT);
        timeOut = 0;
        while (!positionValideVertical(motAPlacer, colonneMot, ligneDebutMot) && timeOut < 10) {
            ligneDebutMot = new Random().nextInt(TAILLE_DEFAUT - motAPlacer.getLongueurMot() + 1);
            colonneMot = new Random().nextInt(TAILLE_DEFAUT);
            timeOut++;
        }
        if (timeOut < 10) {
            placerMotVertical(motAPlacer, colonneMot, ligneDebutMot);
            if (motAPlacer.getSensMot() == Sens.INVERSE) {
                inverseMot(motAPlacer);
            }
            this.listeMotsFinale.add(motAPlacer);
        }
    }


    private void tentativePlacerMotDiagonalBas(Mot motAPlacer) {
        int ligneDebutMot,colonneDebutMot,timeOut;

        if (motAPlacer.getSensMot() == Sens.INVERSE){
            inverseMot(motAPlacer);
        }
        colonneDebutMot = new Random().nextInt(TAILLE_DEFAUT - motAPlacer.getLongueurMot() + 1 );
        ligneDebutMot = new Random().nextInt(TAILLE_DEFAUT-motAPlacer.getLongueurMot()+1);
        timeOut = 0;

        while(!positionValideDiagonalBas(motAPlacer,colonneDebutMot,ligneDebutMot) && timeOut < 10){
            ligneDebutMot = new Random().nextInt(TAILLE_DEFAUT - motAPlacer.getLongueurMot() + 1);
            colonneDebutMot = new Random().nextInt(TAILLE_DEFAUT-motAPlacer.getLongueurMot()+1);
            timeOut++;
        }
        if(timeOut < 10) {
            placerMotDiagonalBas(motAPlacer, colonneDebutMot, ligneDebutMot);
            if (motAPlacer.getSensMot() == Sens.INVERSE){
                inverseMot(motAPlacer);
            }
            this.listeMotsFinale.add(motAPlacer);
        }
    }


    private void tentativePlacerMotDiagonalHaut(Mot motAPlacer) {
        int ligneDebutMot,colonneDebutMot,timeOut;

        if (motAPlacer.getSensMot() == Sens.INVERSE){
            inverseMot(motAPlacer);
        }
        colonneDebutMot = new Random().nextInt(TAILLE_DEFAUT - motAPlacer.getLongueurMot() + 1 );
        ligneDebutMot = new Random().nextInt(TAILLE_DEFAUT-motAPlacer.getLongueurMot()+1) + motAPlacer.getLongueurMot()-1 ;
        timeOut = 0;

        while(!positionValideDiagonalHaut(motAPlacer,colonneDebutMot,ligneDebutMot) && timeOut < 10){
            ligneDebutMot = new Random().nextInt(TAILLE_DEFAUT - motAPlacer.getLongueurMot() + 1) + motAPlacer.getLongueurMot()-1;
            colonneDebutMot = new Random().nextInt(TAILLE_DEFAUT-motAPlacer.getLongueurMot()+1);
            timeOut++;
        }
        if(timeOut < 10) {
            placerMotDiagonalHaut(motAPlacer, colonneDebutMot, ligneDebutMot);
            if (motAPlacer.getSensMot() == Sens.INVERSE){
                inverseMot(motAPlacer);
            }
            this.listeMotsFinale.add(motAPlacer);
        }
    }


    //************************************************************************
    //************************************************************************
    //Methodes testant la validite de la position des mots que l'on veut placer

    private boolean positionValideHorizontal(Mot motAPlacer, int colonneDebutMot, int ligneMot) {
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

    private boolean positionValideVertical(Mot motAPlacer, int colonneMot, int ligneDebutMot) {
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

    private boolean positionValideDiagonalBas(Mot motAPlacer, int colonneDebutMot, int ligneDebutMot) {
        boolean placeLibre = true;
        int i = 0;
        int x = colonneDebutMot;
        int y = ligneDebutMot;
        while (i < motAPlacer.getLongueurMot() && placeLibre){
            String lettre = String.valueOf(motAPlacer.getChaineMot().charAt(i));
            if (!this.grilleCaracteres[x][y].equals(CARACTERE_DEFAUT) && !lettre.equals(this.grilleCaracteres[x][y])){
                placeLibre = false;
            }
            i++;
            x++;
            y++;
        }
        return placeLibre;
    }

    private boolean positionValideDiagonalHaut(Mot motAPlacer, int colonneDebutMot, int ligneDebutMot) {
        boolean placeLibre = true;
        int i = 0;
        int x = colonneDebutMot;
        int y = ligneDebutMot;
        while (i < motAPlacer.getLongueurMot() && placeLibre){
            String lettre = String.valueOf(motAPlacer.getChaineMot().charAt(i));
            if (!this.grilleCaracteres[x][y].equals(CARACTERE_DEFAUT) && !lettre.equals(this.grilleCaracteres[x][y])){
                placeLibre = false;
            }
            i++;
            x++;
            y--;
        }
        return placeLibre;
    }

    //*************************************************************************************
    //*************************************************************************************
    //Methodes de placement des mots dans la grille


    private void placerMotHorizontal(Mot motAPlacer, int colonneDebutMot, int ligneMot) {
        int x = colonneDebutMot;
        for (int i = 0 ; i < motAPlacer.getLongueurMot() ; i++){
            String lettre = String.valueOf(motAPlacer.getChaineMot().charAt(i));
            this.grilleCaracteres[x][ligneMot] = lettre;
            x++;

        }
    }

    private void placerMotVertical(Mot motAPlacer, int colonneMot, int ligneDebutMot) {
        int y = ligneDebutMot;
        for (int i = 0 ; i < motAPlacer.getLongueurMot() ; i++){
            String lettre = String.valueOf(motAPlacer.getChaineMot().charAt(i));
            this.grilleCaracteres[colonneMot][y] = lettre;
            y++;
        }
    }

    private void placerMotDiagonalBas(Mot motAPlacer, int colonneDebutMot, int ligneDebutMot) {
        int x = colonneDebutMot;
        int y = ligneDebutMot;
        for (int i = 0 ; i < motAPlacer.getLongueurMot() ; i++){
            String lettre = String.valueOf(motAPlacer.getChaineMot().charAt(i));
            this.grilleCaracteres[x][y] = lettre;
            x++;
            y++;
        }
    }

    private void placerMotDiagonalHaut(Mot motAPlacer, int colonneDebutMot, int ligneDebutMot) {
        int x = colonneDebutMot;
        int y = ligneDebutMot;
        for (int i = 0 ; i < motAPlacer.getLongueurMot() ; i++){
            String lettre = String.valueOf(motAPlacer.getChaineMot().charAt(i));
            this.grilleCaracteres[x][y] = lettre;
            x++;
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
                    char c = (char) (new Random().nextInt(26) + 'A');
                    this.grilleCaracteres[i][j] = String.valueOf(c);
                }
            }
        }
    }


    public void inverseMot(Mot mot){
        StringBuilder inverse = new StringBuilder();
        inverse.append(mot.getChaineMot());
        inverse.reverse();
        mot.setChaineMot(inverse.toString());

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
