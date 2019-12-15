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

import com.example.info706.Controller.ReviewListener;
import com.example.info706.Controller.OkListener;
import com.example.info706.Model.Mot;
import com.example.info706.Model.Partie;
import com.example.info706.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe de l'activité principale de l'application de mots mêlés
 *
 * @author Brozzoni Vincent / Jugand Théo
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Instance de la classe Partie
     *
     * @see Partie
     */
    private Partie partie;

    /**
     * ListView affichant la liste de mots à trouver
     */
    private ListView listView;

    /**
     * FrameLayout supportant le canevas dessiant la grille du jeu
     */
    private FrameLayout frameLayout;

    /**
     * Chronomètre mesurant le temps d'une partie
     */
    private Chronometer chrono;

    /**
     * Vue de l'image du logo "Pause"
     */
    private ImageView imagePause;

    public Map<String, String> dico;


    /**
     * Bouton d'acceptation
     */
    private Button ok;

    /**
     * Temps écoulé pendant la pause
     */
    private long pause;

    /**
     * Dialogue de définition courante
     */
    private AlertDialog dialog;


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
        this.dico = new HashMap<>();
        this.partie = new Partie(this, this.listView, this.frameLayout, this.chrono, this.imagePause);
        this.partie.demarrageJeu();
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
                this.partie.nouvellePartie();
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
    public void creerListe(){
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

    public void griseItemListView(){
        this.partie.getArrayMotAdapter().notifyDataSetChanged();
    }

    /**
     * Création de la dialogue de l'option "Regles du jeu" du menu
     */
    private void reglesDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setCancelable(false);
        View viewLayout = getLayoutInflater().inflate(R.layout.regles_dialog, null);
        this.ok = viewLayout.findViewById(R.id.ok);
        builder.setView(viewLayout);
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        if (!this.partie.testFinPartie()) {
            this.pause = SystemClock.elapsedRealtime();
            this.chrono.stop();
            this.chrono.setVisibility(View.INVISIBLE);
            this.imagePause.setVisibility(View.VISIBLE);
            this.ok.setOnClickListener(new OkListener(this.chrono, dialog, this.pause, this.imagePause));
        }
        if(this.partie.testFinPartie()){
            this.ok.setOnClickListener((new ReviewListener(dialog)));
        }


    }

    /**
     * Création de la dialogue de l'option "A propos" du menu
     */
    private void aProposDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        View viewLayout = getLayoutInflater().inflate(R.layout.apropos_dialog, null);
        builder.setCancelable(false);
        this.ok = viewLayout.findViewById(R.id.ok);
        builder.setView(viewLayout);
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        if (!this.partie.testFinPartie()) {
            this.pause = SystemClock.elapsedRealtime();
            this.chrono.stop();
            this.chrono.setVisibility(View.INVISIBLE);
            this.imagePause.setVisibility(View.VISIBLE);
            this.ok.setOnClickListener(new OkListener(this.chrono, dialog, this.pause, this.imagePause));
        }
        if(this.partie.testFinPartie()){
            this.ok.setOnClickListener((new ReviewListener(dialog)));
        }
    }

    /**
     * Méthode de création du dictionaire
     * Lis un fichier contenant des mots avec leur définition
     *
     * @return une map de mots/définitions
     */
    public void creerDico(JSONObject jsonObject) {
        try {
            JSONArray listMot = jsonObject.getJSONArray("listMots");
            for (int i = 0; i < listMot.length(); i++) {
                this.dico.put(listMot.getJSONObject(i).getString("name"), listMot.getJSONObject(i).getString("definition"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Création de la dialogue affichant la définition d'un mot dans la liste
     * @param motTrouve
     * le mot sélectionné
     */
    public void afficheDefinitionMot(Mot motTrouve) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        View viewLayout = getLayoutInflater().inflate(R.layout.definition_dialog, null);
        TextView mot = viewLayout.findViewById(R.id.mot);
        TextView def = viewLayout.findViewById(R.id.def);
        builder.setView(viewLayout);
        this.dialog = builder.create();
        mot.setText(motTrouve.getChaineMot());
        def.setText("- " +motTrouve.getDefinition());
        this.dialog.show();
    }


    /**
     * Méthode permettant de créer un dico en fonction des données d'un fichier JSON
     * @param jsonObject Fichier JSON
     */
    public void onResponse(JSONObject jsonObject) {
        this.creerDico(jsonObject);
    }

    /**
     * Getter de la dialogue de définition courante
     * @return
     */
    public AlertDialog getDialog(){
        return this.dialog;
    }
}