package com.example.info706.View;


import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.info706.Controller.ArrayMotAdapter;
import com.example.info706.Model.Direction;
import com.example.info706.Model.Grille;
import com.example.info706.Model.Mot;
import com.example.info706.R;


public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private FrameLayout frameLayout;
    private CanvasGrille canvasGrille;
    private Grille grille;
    private String[] mots = new String[]{
            "CANVAS", "STRING", "ATTRIBUTS", "METHODE", "PARAMETRES", "OBJET",
            "JSON", "ENTREE","JAVA","ADA","ECLIPSE","ANDROID","CLASSE","GETTER",
            "SETTER", "ACTIVITE","GRILLE","FRAME","LAYOUT"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);
        TextView title=(TextView)findViewById(getResources().getIdentifier("action_bar_title", "id", getPackageName()));
        title.setText(R.string.app_name);

        this.listView = (ListView) findViewById(R.id.listViewMot);
        this.frameLayout = (FrameLayout) findViewById(R.id.frameGrilleMot);

        this.nouvellePartie();

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

    public void nouvellePartie(){
        this.grille = new Grille(mots);

        this.canvasGrille = new CanvasGrille(this , this.grille);
        this.frameLayout.addView(this.canvasGrille);

        final ArrayMotAdapter motArrayAdapter = new ArrayMotAdapter(this,this.grille.getListeMotsFinale());
        listView.setAdapter(motArrayAdapter);
    }
}
