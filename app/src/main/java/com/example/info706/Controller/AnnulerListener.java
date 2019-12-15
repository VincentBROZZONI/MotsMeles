package com.example.info706.Controller;

import android.support.v7.app.AlertDialog;
import android.view.View;

import com.example.info706.Model.Partie;

/**
 * Classe permettant de relancer le chronomètre ainsi que la partie
 * @author Brozzoni Vincent / Jugand Théo
 */
public class AnnulerListener implements View.OnClickListener{

        private Partie partie;
        private AlertDialog dialog;

        public AnnulerListener(Partie partie, AlertDialog dialog ) {
            this.partie = partie;
            this.dialog = dialog;
        }

        @Override
        public void onClick(View v) {
            this.partie.reprendre();
            this.dialog.cancel();
        }
}
