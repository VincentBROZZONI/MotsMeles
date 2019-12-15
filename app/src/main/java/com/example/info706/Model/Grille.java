package com.example.info706.Model;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

/**
 * Classe de la grille de mot
 *
 * @author Brozzoni Vincent / Jugand Théo
 */
public class Grille {

    /**
     * Constante du nombre de lignes de la grille
     */
    private static final int HAUTEUR_DEFAUT = 9 ;

    /**
     * Constante du nombre de colonnes de la grille
     */
    private static final int LARGEUR_DEFAUT = 12 ;

    /**
     * Constante du caractère par défaut lors de l'initialistion de la grille
     */
    private static final String CARACTERE_DEFAUT = " ";

    /**
     * Constante du nombre de mots à trouver dans la grille
     */
    private static final int NOMBRE_MOTS_PARTIE = 16 ;

    /**
     * La matrice contenant les caractères de la grille
     */
    private String[][] grilleCaracteres;

    /**
     * La liste de tous les mots d'un thème
     */
    private ArrayList<Mot> listeMots;

    /**
     * La liste des mots placés dans la grille à chercher
     */
    private ArrayList<Mot> listeMotsFinale;

    /**
     * Constructeur de la grille
     * @param dico
     * le dictionnaire contenant les mots associés à leur définition
     */
    public Grille(Map<String,String> dico){
        this.listeMots = new ArrayList<>();
        Iterator it = dico.keySet().iterator();
        while(it.hasNext()) {
            Object cle = it.next();
            Mot temp = new Mot(cle.toString(), cle.toString().length(), Direction.randomDirection(), Sens.randomSens(), dico.get(cle) , false, Color.WHITE);
            this.listeMots.add(temp);
        }
        this.inititialiseGrille();
        this.genererGrille();
    }

    /**
     * Méthode initialisant la matrice de caractères avec le caractère par défaut
     */
    public void inititialiseGrille (){
        this.grilleCaracteres = new String[LARGEUR_DEFAUT][HAUTEUR_DEFAUT];
        int i,j;
        for (i = 0 ; i < LARGEUR_DEFAUT ; i++){
            for(j = 0 ; j < HAUTEUR_DEFAUT; j++){
                this.grilleCaracteres[i][j] = CARACTERE_DEFAUT;
            }
        }
    }

    /**
     * Méthode générant la grille et la liste de mots à trouver
     */
    public void genererGrille(){
        this.listeMotsFinale = new ArrayList<>();
        int i = 0;

        while(this.listeMotsFinale.size()< NOMBRE_MOTS_PARTIE && i< this.listeMots.size()){

            Mot motAPlacer = this.listeMots.get(i);

            switch(motAPlacer.getDirectionMot()){
                case HORIZONTAL:
                    if(motAPlacer.getLongueurMot()<= LARGEUR_DEFAUT) {
                        tentativePlacerMotHorizontal(motAPlacer);
                    }
                    break;
                case VERTICAL:
                    if(motAPlacer.getLongueurMot()<= HAUTEUR_DEFAUT) {
                        tentativePlacerMotVertical(motAPlacer);
                    }
                    break;
                case DIAGONAL_BAS:
                    if(motAPlacer.getLongueurMot()<= HAUTEUR_DEFAUT) {
                        tentativePlacerMotDiagonalBas(motAPlacer);
                    }
                    break;
                case DIAGONAL_HAUT:
                    if(motAPlacer.getLongueurMot()<= HAUTEUR_DEFAUT) {
                        tentativePlacerMotDiagonalHaut(motAPlacer);
                    }
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

    /**
     * Méthode de tentative de placement d'un mot horizontalement
     * @param motAPlacer
     * le mot à placer
     */
    private void tentativePlacerMotHorizontal(Mot motAPlacer) {
        int colonneDebutMot,ligneMot,timeOut;

        if (motAPlacer.getSensMot() == Sens.INVERSE){
            inverseMot(motAPlacer);
        }
        colonneDebutMot = new Random().nextInt(LARGEUR_DEFAUT - motAPlacer.getLongueurMot()+1);
        ligneMot = new Random().nextInt(HAUTEUR_DEFAUT);
        timeOut = 0;

        while (!positionValideHorizontal(motAPlacer, colonneDebutMot, ligneMot) && timeOut < 10) {
            colonneDebutMot = new Random().nextInt(LARGEUR_DEFAUT - motAPlacer.getLongueurMot() + 1);
            ligneMot = new Random().nextInt(HAUTEUR_DEFAUT);
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

    /**
     * Méthode de tentative de placement d'un mot verticalement
     * @param motAPlacer
     * le mot à placer
     */
    private void tentativePlacerMotVertical(Mot motAPlacer) {

        int ligneDebutMot, colonneMot, timeOut;

        if (motAPlacer.getSensMot() == Sens.INVERSE) {
            inverseMot(motAPlacer);
        }

        ligneDebutMot = new Random().nextInt(HAUTEUR_DEFAUT - motAPlacer.getLongueurMot() + 1);
        colonneMot = new Random().nextInt(LARGEUR_DEFAUT);
        timeOut = 0;
        while (!positionValideVertical(motAPlacer, colonneMot, ligneDebutMot) && timeOut < 10) {
            ligneDebutMot = new Random().nextInt(HAUTEUR_DEFAUT - motAPlacer.getLongueurMot() + 1);
            colonneMot = new Random().nextInt(LARGEUR_DEFAUT);
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

    /**
     * Méthode de tentative de placement d'un mot en diagonale d'en haut à gauche jusqu'en bas à droite
     * @param motAPlacer
     * le mot à placer
     */
    private void tentativePlacerMotDiagonalBas(Mot motAPlacer) {
        int ligneDebutMot,colonneDebutMot,timeOut;

        if (motAPlacer.getSensMot() == Sens.INVERSE){
            inverseMot(motAPlacer);
        }
        colonneDebutMot = new Random().nextInt(LARGEUR_DEFAUT - motAPlacer.getLongueurMot() + 1 );
        ligneDebutMot = new Random().nextInt(HAUTEUR_DEFAUT - motAPlacer.getLongueurMot()+1);
        timeOut = 0;

        while(!positionValideDiagonalBas(motAPlacer,colonneDebutMot,ligneDebutMot) && timeOut < 10){
            ligneDebutMot = new Random().nextInt(HAUTEUR_DEFAUT - motAPlacer.getLongueurMot() + 1);
            colonneDebutMot = new Random().nextInt(LARGEUR_DEFAUT-motAPlacer.getLongueurMot()+1);
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

    /**
     * Méthode de tentative de placement d'un mot en diagonale d'en bas à gauche jusqu'en haut à droite
     * @param motAPlacer
     * le mot à placer
     */
    private void tentativePlacerMotDiagonalHaut(Mot motAPlacer) {
        int ligneDebutMot,colonneDebutMot,timeOut;

        if (motAPlacer.getSensMot() == Sens.INVERSE){
            inverseMot(motAPlacer);
        }
        colonneDebutMot = new Random().nextInt(LARGEUR_DEFAUT - motAPlacer.getLongueurMot() + 1 );
        ligneDebutMot = new Random().nextInt(HAUTEUR_DEFAUT-motAPlacer.getLongueurMot()+1) + motAPlacer.getLongueurMot()-1 ;
        timeOut = 0;

        while(!positionValideDiagonalHaut(motAPlacer,colonneDebutMot,ligneDebutMot) && timeOut < 10){
            ligneDebutMot = new Random().nextInt(HAUTEUR_DEFAUT - motAPlacer.getLongueurMot() + 1) + motAPlacer.getLongueurMot()-1;
            colonneDebutMot = new Random().nextInt(LARGEUR_DEFAUT-motAPlacer.getLongueurMot()+1);
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

    /**
     * Méthode vérifiant si la position où l'on veut placer horizontalement le mot est valide
     * @param motAPlacer
     * le mot à placer
     * @param colonneDebutMot
     * la colonne où commence le mot
     * @param ligneMot
     * la ligne où on tente de placer le mot
     * @return
     * un booleen indiquant si la position est valide
     */
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

    /**
     * Méthode vérifiant si la position où l'on veut placer verticalement le mot est valide
     * @param motAPlacer
     * le mot à placer
     * @param colonneMot
     * la colonne où on tente de placer le mot
     * @param ligneDebutMot
     * la ligne où commence le mot
     * @return
     * un booleen indiquant si la position est valide
     */
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

    /**
     * Méthode vérifiant si la position où l'on veut placer diagonalement vers le bas le mot est valide
     * @param motAPlacer
     * le mot à placer
     * @param colonneDebutMot
     * la colonne où commence le mot
     * @param ligneDebutMot
     * la ligne où commence le mot
     * @return
     * un booleen indiquant si la position est valide
     */
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

    /**
     * Méthode vérifiant si la position où l'on veut placer diagonalement vers le haut le mot est valide
     * @param motAPlacer
     * le mot à placer
     * @param colonneDebutMot
     * la colonne où commence le mot
     * @param ligneDebutMot
     * la ligne où commence le mot
     * @return
     * un booleen indiquant si la position est valide
     */
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

    /**
     * Méthode de placement horizontal des caractères du mot dans la matrice de caractères de la grille
     * @param motAPlacer
     * le mot à placer
     * @param colonneDebutMot
     * la colonne où commence le mot
     * @param ligneMot
     * la ligne où sera placé le mot
     */
    private void placerMotHorizontal(Mot motAPlacer, int colonneDebutMot, int ligneMot) {
        int x = colonneDebutMot;
        for (int i = 0 ; i < motAPlacer.getLongueurMot() ; i++){
            String lettre = String.valueOf(motAPlacer.getChaineMot().charAt(i));
            this.grilleCaracteres[x][ligneMot] = lettre;
            x++;

        }
    }

    /**
     * Méthode de placement vertical des caractères du mot dans la matrice de caractères de la grille
     * @param motAPlacer
     * le mot à placer
     * @param colonneMot
     * la colonne où sera placé le mot
     * @param ligneDebutMot
     * la ligne où commence le mot
     */
    private void placerMotVertical(Mot motAPlacer, int colonneMot, int ligneDebutMot) {
        int y = ligneDebutMot;
        for (int i = 0 ; i < motAPlacer.getLongueurMot() ; i++){
            String lettre = String.valueOf(motAPlacer.getChaineMot().charAt(i));
            this.grilleCaracteres[colonneMot][y] = lettre;
            y++;
        }
    }

    /**
     * Méthode de placement diagonal vers le bas des caractères du mot dans la matrice de caractères de la grille
     * @param motAPlacer
     * le mot à placer
     * @param colonneDebutMot
     * la colonne où commence le mot
     * @param ligneDebutMot
     * la ligne où commence le mot
     */
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

    /**
     * Méthode de placement diagonal vers le haut des caractères du mot dans la matrice de caractères de la grille
     * @param motAPlacer
     * le mot à placer
     * @param colonneDebutMot
     * la colonne où commence le mot
     * @param ligneDebutMot
     * la ligne où commence le mot
     */
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

    /**
     * Méthode de placement de caractères aléatoires dans les cases vides de la matrice de caractères
     */
    public void remplirCasesRestantes(){
        for(int i = 0 ; i < LARGEUR_DEFAUT ; i++){
            for(int j = 0 ; j< HAUTEUR_DEFAUT ; j++){
                if(this.grilleCaracteres[i][j].equals(CARACTERE_DEFAUT)){
                    char c = (char) (new Random().nextInt(26) + 'A');
                    this.grilleCaracteres[i][j] = String.valueOf(c);
                }
            }
        }
    }

    /**
     * Méthode inversant les caractères d'un mot
     * @param mot
     * le mot que l'on inverse
     */
    public void inverseMot(Mot mot){
        StringBuilder inverse = new StringBuilder();
        inverse.append(mot.getChaineMot());
        inverse.reverse();
        mot.setChaineMot(inverse.toString());
    }

    /**
     * Getter du nombre de lignes de la grille
     * @return
     * un entier
     */
    public static int getHauteurDefaut() {
        return HAUTEUR_DEFAUT;
    }

    /**
     * Getter du nombre de colonnes de la grille
     * @return
     * un entier
     */
    public static int getLargeurDefaut() {
        return LARGEUR_DEFAUT;
    }

    /**
     * Getter de la matrice de caractères de la grille
     * @return
     * un tableau de caractères à deux dimensions
     */
    public String[][] getGrilleCaracteres() {
        return grilleCaracteres;
    }

    /**
     * Getter de la liste de mots à trouver
     * @return
     * une liste de Mot
     */
    public ArrayList<Mot> getListeMotsFinale() {
        return listeMotsFinale;
    }

    /**
     * Getter d'un mot dans la liste de mots à trouver correspondant à une chaine de caractères
     * @param mot
     * la chaine de caractères correspondant au mot à trouver
     * @return
     * un mot
     */
    public Mot getMotByString(String mot){
        Mot motRecupere = new Mot();
        Iterator<Mot> iterator = this.listeMotsFinale.iterator();
        while (iterator.hasNext()){
            Mot motCourant = iterator.next();
            if(motCourant.getChaineMot().equals(mot)){
                motRecupere = motCourant;
            }
        }
        return motRecupere;
    }
}