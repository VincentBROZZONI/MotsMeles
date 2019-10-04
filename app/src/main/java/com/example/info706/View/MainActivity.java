package com.example.info706.View;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.example.info706.Model.Grille;
import com.example.info706.R;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private FrameLayout frameLayout;
    private CanvasGrille canvasGrille;
    private Grille grille;
    private String[] mots = new String[]{
            "CANVAS", "STRING", "ATTRIBUTS", "METHODE", "PARAMETRES", "OBJET",
            "JSON", "ENTREE","JAVA","ADA","ECLIPSE","ANDROID"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.listView = (ListView) findViewById(R.id.listViewMot);
        this.frameLayout = (FrameLayout) findViewById(R.id.frameGrilleMot);

        this.grille = new Grille(mots);

        this.canvasGrille = new CanvasGrille(this , this.grille);
        this.frameLayout.addView(this.canvasGrille);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1,mots);
        listView.setAdapter(adapter);
    }

}
