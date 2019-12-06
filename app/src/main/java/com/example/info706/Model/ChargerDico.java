package com.example.info706.Model;

import java.util.HashMap;
import java.util.Map;

public class ChargerDico {

    private static String[] mots = new String[]{
            "CANVAS", "STRING", "ATTRIBUTS", "METHODE", "PARAMETRES", "OBJET",
            "JSON","JAVA","ADA","ECLIPSE","ANDROID","CLASSE","GETTER",
            "SETTER", "ACTIVITE","GRILLE","FRAME","LAYOUT"};
    private static String[] def = new String[]{
            "Canevas permettant de dessiner dans un layout",
            "Classe gérant les chaînes de caractère",
            "Paramêtres d'une classe JAVA",
            "Fonction d'une classe JAVA",
            "Objets donnés à une méthode",
            "Instance d'une classe",
            "Format de données textuelles dérivé de la notation des objets du langage JavaScript",
            "Défini à l'origine comme un langage de programmation, Java a évolué pour devenir un ensemble cohérent d'éléments techniques et non techniques",
            "Ada est un langage de programmation orienté objet dont les premières versions remontent au début des années 1980"};

    public static Map<String,String> creerDico() {
        Map<String,String> dico = new HashMap<>();
        int i = 0;
        while (i < mots.length && i< def.length){
            dico.put(mots[i],def[i]);
            i++;
        }
        return dico;
    }
}