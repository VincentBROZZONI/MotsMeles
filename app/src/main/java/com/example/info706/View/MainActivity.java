package com.example.info706.View;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.example.info706.Model.Direction;
import com.example.info706.Model.Grille;
import com.example.info706.Model.Mot;
import com.example.info706.Model.Sens;
import com.example.info706.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private FrameLayout frameLayout;
    private CanvasGrille canvasGrille;
    private Grille grille;
    private String[] mots = new String[]{
            "Canvas", "String", "Attributs", "Méthode", "Parametres", "Objet",
            "Json", "Entree"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.listView = (ListView) findViewById(R.id.listViewMot);
        this.frameLayout = (FrameLayout) findViewById(R.id.frameGrilleMot);

        this.grille = new Grille();

        this.canvasGrille = new CanvasGrille(this , this.grille);
        this.frameLayout.addView(this.canvasGrille);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1, mots);
        listView.setAdapter(adapter);
    }

    /*public Mot StringVersMot (String string){

        Mot mot = new Mot(string,string.length(), Direction.HORIZONTAL, Sens.DROIT);

        return mot;
    }

    public ArrayList<Mot> StringVersArray (){

        ArrayList<Mot> listeMots = new ArrayList<>();
        for (int i = 0 ; i < 8; i++){
            listeMots.add(StringVersMot(this.mots[i]));
        }

        return listeMots;
    }*/
}
