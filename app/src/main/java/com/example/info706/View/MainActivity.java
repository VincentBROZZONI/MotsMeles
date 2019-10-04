package com.example.info706.View;


import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.info706.Controller.ArrayMotAdapter;
import com.example.info706.Model.Direction;
import com.example.info706.Model.Grille;
import com.example.info706.Model.Mot;
import com.example.info706.R;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private FrameLayout frameLayout;
    private CanvasGrille canvasGrille;
    private Grille grille;
    private Map<String,String> dico;

    private String[] mots = new String[]{
            "CANVAS", "STRING", "ATTRIBUTS", "METHODE", /*"PARAMETRES", "OBJET",
            "JSON", "ENTREE","JAVA","ADA","ECLIPSE","ANDROID","CLASSE","GETTER",
            "SETTER", "ACTIVITE","GRILLE","FRAME","LAYOUT"*/};
    private String[] def = new String[]{"Canevas permettant de dessiner dans un layout",
                                        "Type chaîne de caractère",
                                        "Paramêtres d'une classe JAVA",
                                        "Fonction d'une classe JAVA"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar barreOutils = (Toolbar) findViewById(R.id.barre_outils_menu);
        setSupportActionBar(barreOutils);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        this.listView = (ListView) findViewById(R.id.listViewMot);
        this.frameLayout = (FrameLayout) findViewById(R.id.frameGrilleMot);

        this.creerDico(this.mots , this.def);

        this.nouvellePartie();

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //ajoute les entrées de menu_test à l'ActionBar
        getMenuInflater().inflate(R.menu.menu_jeu, menu);
        return true;
    }

    //gère le click sur une action de l'ActionBar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_reset:
                    nouvellePartie();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }


    private void creerDico(String[] mots, String[] def) {
        this.dico = new HashMap<>();
        int i = 0;
        while (i < mots.length && i< def.length){
            this.dico.put(mots[i],def[i]);
            i++;
        }
    }


    public void nouvellePartie(){
        this.grille = new Grille(this.dico);

        this.canvasGrille = new CanvasGrille(this , this.grille);
        this.frameLayout.addView(this.canvasGrille);

        final ArrayMotAdapter motArrayAdapter = new ArrayMotAdapter(this,this.grille.getListeMotsFinale());
        listView.setAdapter(motArrayAdapter);
    }
}
