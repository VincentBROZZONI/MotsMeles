package com.example.info706.View;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.info706.Controller.AnnulerListener;
import com.example.info706.Controller.ArrayMotAdapter;
import com.example.info706.Controller.DemarrerListener;
import com.example.info706.Model.Grille;
import com.example.info706.Model.Mot;
import com.example.info706.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private FrameLayout frameLayout;
    private CanvasGrille canvasGrille;
    private Grille grille;
    private Chronometer chrono ;
    private ImageView imagePause;
    private long pause;
    private Button demarrer;
    private Button annuler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar barreOutils = (Toolbar) findViewById(R.id.barre_outils_menu);
        setSupportActionBar(barreOutils);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        this.listView = (ListView) findViewById(R.id.listViewMot);
        this.frameLayout = (FrameLayout) findViewById(R.id.frameGrilleMot);
        this.chrono = (Chronometer) findViewById(R.id.chronometer);
        this.imagePause = (ImageView) findViewById(R.id.pause);
        this.chrono.setVisibility(View.INVISIBLE);
        this.imagePause.setVisibility(View.INVISIBLE);

        this.demarrageJeu();
    }

    @Override
    public void onBackPressed(){
        //empêche le retour en arrière
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //ajoute les entrées de menu_test à l'ActionBar
        getMenuInflater().inflate(R.menu.menu_jeu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //gère le click sur une action de l'ActionBar
        switch (item.getItemId()){
            case R.id.action_reset:
                this.nouvellePartie();
                break;
            case R.id.action_info:
                this.reglesDialog();
                break;
            case R.id.action_about:
                this.aProposDialog();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Méthode de création de la listeView comprenant les mots à chercher
     */
    private void creerListe(){
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Mot motSelect = (Mot) parent.getItemAtPosition(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                View viewLayout = getLayoutInflater().inflate(R.layout.definition_dialog,null);
                TextView mot = viewLayout.findViewById(R.id.mot);
                TextView def = viewLayout.findViewById(R.id.def);
                builder.setView(viewLayout);
                AlertDialog dialog = builder.create();
                mot.setText(motSelect.getChaineMot());
                def.setText("- " + motSelect.getDefinition());
                dialog.show();
            }
        });
    }

    /**
     * Méthode de démarrage d'une partie
     * Crée la liste des mots à chercher et une dialogue de confirmation de lancement de partie
     */
    public void demarrageJeu(){

        this.creerListe();
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setCancelable(false);
        View viewLayout = getLayoutInflater().inflate(R.layout.bienvenue_dialog,null);
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

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setCancelable(false);
        View viewLayout = getLayoutInflater().inflate(R.layout.demarrer_dialog,null);
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
        this.grille = new Grille(this.creerDico());
        this.canvasGrille = new CanvasGrille(this , this.grille);
        this.frameLayout.addView(this.canvasGrille);
        this.canvasGrille.setMainActivity(this);
        this.chrono.setBase(SystemClock.elapsedRealtime());
        this.chrono.start();
        this.chrono.setVisibility(View.VISIBLE);
        this.imagePause.setVisibility(View.INVISIBLE);

        final ArrayMotAdapter motArrayAdapter = new ArrayMotAdapter(this,this.grille.getListeMotsFinale());
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
     * Méthode permettant de "griser" un mot trouvé
     * @param index
     * l'index du mot dans la liste
     * @param color
     * la couleur dans laquelle on grise le mot
     */
    public void griseItemListView(int index,int color){
        this.listView.getChildAt(index).setBackgroundColor(color);
    }

    /**
     * Création de la dialogue de l'option "Regles du jeu" du menu
     */
    private void reglesDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        View viewLayout = getLayoutInflater().inflate(R.layout.regles_dialog,null);
        builder.setView(viewLayout);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Création de la dialogue de l'option "A propos" du menu
     */
    private void aProposDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        View viewLayout = getLayoutInflater().inflate(R.layout.apropos_dialog,null);
        builder.setView(viewLayout);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Méthode de création du dictionaire
     * Lis un fichier contenant des mots avec leur définition
     * @return
     * une map de mots/définitions
     */
    public Map<String,String> creerDico() {
        Map<String,String> dico = new HashMap<>();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(getAssets().open("mots.txt")));
            String st;
            while ((st = br.readLine()) != null) {
                String[] parts = st.split("=");
                dico.put(parts[0],parts[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dico;
    }
}