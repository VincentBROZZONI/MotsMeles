package com.example.info706.Controller;

import android.support.v7.app.AlertDialog;
import android.view.View;

import com.example.info706.Model.Partie;

public class DemarrerListener implements View.OnClickListener {

    private Partie partie;
    private AlertDialog dialog;

    public DemarrerListener(Partie partie, AlertDialog dialog) {
        this.partie = partie;
        this.dialog = dialog;
    }

    @Override
    public void onClick(View v) {
        this.partie.creerNouvellePartie();
        this.dialog.cancel();
    }
}
