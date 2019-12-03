package com.example.info706.Controller;

import android.support.v7.app.AlertDialog;
import android.view.View;
import com.example.info706.View.MainActivity;

public class AnnulerListener implements View.OnClickListener{

        private MainActivity activity;
        private AlertDialog dialog;

        public AnnulerListener(MainActivity mainActivity, AlertDialog dialog ) {
            this.activity = mainActivity;
            this.dialog = dialog;
        }

        @Override
        public void onClick(View v) {
            this.activity.reprendre();
            this.dialog.cancel();
        }
}
