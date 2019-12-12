package com.example.info706.Model;

import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.info706.Controller.AnnulerListener;
import com.example.info706.Controller.ArrayMotAdapter;
import com.example.info706.Controller.DemarrerListener;
import com.example.info706.Controller.HTTPJsonGetter;
import com.example.info706.R;
import com.example.info706.View.CanvasGrille;
import com.example.info706.View.MainActivity;

import java.util.concurrent.TimeUnit;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Classe gérant le système de partie
 */
public class Partie {

    /**
     * Instance de l'activité principale
     */
    private MainActivity mainActivity;

    /**
     * Instance de la classe Grille
     * @see Grille
     */
    private Grille grille;

    /**
     * Instance de la classe CanvasGrille
     * @see CanvasGrille
     */
    private CanvasGrille canvasGrille;

    /**
     * ListView affichant les mots à trouver
     */
    private ListView listView;

    /**
     * FrameLayout supportant le canevas dessiant la grille du jeu
     */
    private FrameLayout frameLayout;

    /**
     * Chronomètre mesurant le temps d'une partie
     */
    private Chronometer chrono ;

    /**
     * Temps écoulé pendant la pause
     */
    private long pause;

    /**
     * Bouton permettant de lancer une partie
     */
    private Button demarrer;

    /**
     * Bouton permettant de fermer la dialogue pour lancer une nouvelle partie et de relancer le chronomètre
     */
    private Button annuler;

    /**
     * Vue de l'image du logo "Pause"
     */
    private ImageView imagePause;

    private ArrayMotAdapter motArrayAdapter;

    /**
     * Constructeur
     * @param mainActivity
     * une activité de type MainActivity
     * @param listView
     * une ListView
     * @param frameLayout
     * un FrameLayout
     * @param chrono
     * un Chronometer
     * @param imagePause
     * Une ImageView
     */
    public Partie(MainActivity mainActivity , ListView listView, FrameLayout frameLayout, Chronometer chrono , ImageView imagePause){
        this.mainActivity = mainActivity;
        this.listView = listView;
        this.frameLayout=frameLayout;
        this.chrono=chrono;
        this.imagePause = imagePause;
        HTTPJsonGetter jsonGetter = new HTTPJsonGetter("http://www.lesageolivier.fr/motsmeles/get.php", this.mainActivity);
        jsonGetter.execute();
    }

    /**
     * Méthode de démarrage d'une partie
     * Crée la liste des mots à chercher et une dialogue de confirmation de lancement de partie
     */
    public void demarrageJeu(){
        this.mainActivity.creerListe();
        AlertDialog.Builder builder = new AlertDialog.Builder(this.mainActivity);
        builder.setCancelable(false);
        View viewLayout = this.mainActivity.getLayoutInflater().inflate(R.layout.bienvenue_dialog,null);
        this.demarrer = viewLayout.findViewById(R.id.demarrer);
        builder.setView(viewLayout);
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        this.demarrer.setOnClickListener(new DemarrerListener(this,dialog));
    }

    /**
     * Méthode de création de la dialogue permettant de démarrer une nouvelle partie
     */
    public void nouvellePartie(){
        this.pause = SystemClock.elapsedRealtime();
        this.chrono.stop();
        this.chrono.setVisibility(View.INVISIBLE);
        this.imagePause.setVisibility(View.VISIBLE);
        AlertDialog.Builder builder = new AlertDialog.Builder(this.mainActivity);
        builder.setCancelable(false);
        View viewLayout = this.mainActivity.getLayoutInflater().inflate(R.layout.demarrer_dialog,null);
        this.demarrer = viewLayout.findViewById(R.id.demarrer);
        this.annuler = viewLayout.findViewById(R.id.annuler);
        builder.setView(viewLayout);
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        this.demarrer.setOnClickListener(new DemarrerListener(this,dialog));
        this.annuler.setOnClickListener(new AnnulerListener(this,dialog));
    }

    /**
     * Méthode pour recommencer une partie avec une nouvelle grille et
     * nouvelle liste de mots et remise à zero du chronomètre
     */
    public void creerNouvellePartie(){
        this.grille = new Grille(this.mainActivity.dico);
        this.canvasGrille = new CanvasGrille(this.mainActivity , this.grille ,this.mainActivity,this );
        this.frameLayout.addView(this.canvasGrille);
        this.chrono.setBase(SystemClock.elapsedRealtime());
        this.chrono.start();
        this.chrono.setVisibility(View.VISIBLE);
        this.imagePause.setVisibility(View.INVISIBLE);
        this.motArrayAdapter = new ArrayMotAdapter(this.mainActivity,this.grille.getListeMotsFinale());
        this.listView.setAdapter(motArrayAdapter);
    }

    /**
     * Méthode permettant de redémarrer le chronomètre
     */
    public void reprendre(){
        this.chrono.setBase(this.chrono.getBase() + SystemClock.elapsedRealtime() - this.pause);
        this.chrono.start();
        this.chrono.setVisibility(View.VISIBLE);
        this.imagePause.setVisibility(View.INVISIBLE);
    }

    /**
     * Méthode testant si tous les mots de la liste ont été trouvé
     * @return
     * booléen indiquant si tous les mots ont été trouvé
     */
    public boolean testFinPartie(){
        boolean toutTrouve = true;
        int i = 0;
        while (i < this.grille.getListeMotsFinale().size() && toutTrouve){
            if(!this.grille.getListeMotsFinale().get(i).getTrouve()){
                toutTrouve = false;
            }
            i++;
        }
        return toutTrouve;
    }

    /**
     * Méthode affichant la dialogue de fin de partie permettant de relancer une nouvelle partie
     */
    public void finPartie(){
        if(testFinPartie()){
            this.chrono.stop();
            this.chrono.setVisibility(View.INVISIBLE);
            long elapsedMillis = SystemClock.elapsedRealtime() - this.chrono.getBase();
            AlertDialog.Builder builder = new AlertDialog.Builder(this.mainActivity);
            builder.setCancelable(false);
            View viewLayout = this.mainActivity.getLayoutInflater().inflate(R.layout.fin_dialog,null);
            this.demarrer = viewLayout.findViewById(R.id.demarrer);
            TextView temps = viewLayout.findViewById(R.id.temps);
            builder.setView(viewLayout);
            AlertDialog dialog = builder.create();
            temps.setText(String.format("%d min et %d sec",
                    TimeUnit.MILLISECONDS.toMinutes(elapsedMillis),
                    TimeUnit.MILLISECONDS.toSeconds(elapsedMillis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(elapsedMillis))));
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            this.demarrer.setOnClickListener(new DemarrerListener(this,dialog));
        }
    }

    public ArrayMotAdapter getArrayMotAdapter(){ return this.motArrayAdapter;}
}
