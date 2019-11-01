package com.example.info706.Controller;

import android.support.v7.app.AlertDialog;
import android.view.View;
import com.example.info706.View.MainActivity;

public class DemarrerListener implements View.OnClickListener {

    private MainActivity activity;
    private AlertDialog dialog;

    public DemarrerListener(MainActivity mainActivity, AlertDialog dialog) {
        this.activity = mainActivity;
        this.dialog = dialog;
    }

    @Override
    public void onClick(View v) {
        this.activity.creerNouvellePartie();
        this.dialog.cancel();
    }
}
