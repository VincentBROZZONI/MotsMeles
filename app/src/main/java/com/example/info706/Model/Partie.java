package com.example.info706.Model;

import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.info706.Controller.AnnulerListener;
import com.example.info706.Controller.ArrayMotAdapter;
import com.example.info706.Controller.DemarrerListener;
import com.example.info706.R;
import com.example.info706.View.CanvasGrille;
import com.example.info706.View.MainActivity;

public class Partie {

    private MainActivity mainActivity;
    private Grille grille;
    private CanvasGrille canvasGrille;
    private ListView listView;
    private FrameLayout frameLayout;
    private Chronometer chrono ;
    private long pause;
    private Button demarrer;
    private Button annuler;
    private ImageView imagePause;

    public Partie(MainActivity mainActivity , ListView listView, FrameLayout frameLayout, Chronometer chrono , ImageView imagePause){
        this.mainActivity = mainActivity;
        this.listView = listView;
        this.frameLayout=frameLayout;
        this.chrono=chrono;
        this.imagePause = imagePause;
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
        this.grille = new Grille(this.mainActivity.creerDico());
        this.canvasGrille = new CanvasGrille(this.mainActivity , this.grille ,this.mainActivity,this );
        this.frameLayout.addView(this.canvasGrille);
        this.chrono.setBase(SystemClock.elapsedRealtime());
        this.chrono.start();
        this.chrono.setVisibility(View.VISIBLE);
        this.imagePause.setVisibility(View.INVISIBLE);
        final ArrayMotAdapter motArrayAdapter = new ArrayMotAdapter(this.mainActivity,this.grille.getListeMotsFinale());
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

    public void finPartie(){
        if(testFinPartie()){
            this.chrono.stop();
            AlertDialog.Builder builder = new AlertDialog.Builder(this.mainActivity);
            builder.setCancelable(false);
            View viewLayout = this.mainActivity.getLayoutInflater().inflate(R.layout.fin_dialog,null);
            this.demarrer = viewLayout.findViewById(R.id.demarrer);
            builder.setView(viewLayout);
            AlertDialog dialog = builder.create();
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            this.demarrer.setOnClickListener(new DemarrerListener(this,dialog));
        }
    }
}
