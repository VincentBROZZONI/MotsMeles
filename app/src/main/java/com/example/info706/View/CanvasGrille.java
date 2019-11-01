package com.example.info706.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import com.example.info706.Model.Grille;
import com.example.info706.R;


public class CanvasGrille extends View {

    private Paint paint;
    private Grille grille;

    public CanvasGrille(Context context, Grille grille) {
        super(context);
        this.paint = new Paint();
        this.grille = grille;
    }

    @Override
    public void onDraw (Canvas canvas){
            super.onDraw(canvas);
            canvas.drawColor(getResources().getColor(R.color.colorBackground));
            this.paint.setColor(Color.WHITE);
            this.paint.setTextSize(60);
            int departX = 250;
            int departY = 170;
            int decalageX = 0;
            int decalageY = 0;

            int i,j;
            for (i = 0 ; i < Grille.getTailleDefaut() ; i++){
                for ( j = 0 ; j < Grille.getTailleDefaut() ; j++){
                    canvas.drawText(this.grille.getGrilleCaracteres()[i][j],departX + decalageX , departY + decalageY ,this.paint);
                    decalageY += 80;
                }
                decalageY = 0;
                decalageX += 120;
            }
    }

}
