package com.example.info706.Controller;

import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;

public class okListener implements View.OnClickListener {

    private AlertDialog dialog;
    private Chronometer chrono;
    private long pause;
    private ImageView imagePause;

    public okListener(Chronometer chrono, AlertDialog dialog,long pause , ImageView imagePause ) {
        this.dialog = dialog;
        this.chrono = chrono;
        this.pause = pause;
        this.imagePause= imagePause;
    }

    @Override
    public void onClick(View v) {
        this.chrono.setBase(this.chrono.getBase() + SystemClock.elapsedRealtime() - this.pause);
        this.chrono.start();
        this.chrono.setVisibility(View.VISIBLE);
        this.imagePause.setVisibility(View.INVISIBLE);
        this.dialog.cancel();
    }
}

