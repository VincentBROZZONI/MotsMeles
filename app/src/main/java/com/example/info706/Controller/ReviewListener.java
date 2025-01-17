package com.example.info706.Controller;

import android.support.v7.app.AlertDialog;
import android.view.View;

/**
 * Classe permettant de fermer une dialog
 * @author Brozzoni Vincent / Jugand Théo
 */
public class ReviewListener implements View.OnClickListener {
    private AlertDialog dialog;
    public ReviewListener(AlertDialog dialog) {
        this.dialog = dialog;
    }

    @Override
    public void onClick(View v) {
        this.dialog.cancel();
    }
}
