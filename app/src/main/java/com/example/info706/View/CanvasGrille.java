package com.example.info706.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.View;

import com.example.info706.Model.Grille;


public class CanvasGrille extends View {

    private Paint paint;
    private Paint paint2;
    private Grille grille;
    private Path path;

    public CanvasGrille(Context context, Grille grille) {
        super(context);
        this.paint = new Paint();
        this.paint2 = new Paint();
        this.path = new Path();
        this.grille = grille;
        paint2.setAntiAlias(true);
    }

    @Override
    public void onDraw (Canvas canvas){
            super.onDraw(canvas);
            canvas.drawColor(Color.rgb(48,48,48));
            this.paint.setColor(Color.WHITE);
            this.paint.setTextSize(60);
            int departX = 200;
            int departY = 120;
            int decalageX = 0;
            int decalageY = 0;

            int i,j;
            for (i = 0 ; i < Grille.getTailleDefaut() ; i++){
                for ( j = 0 ; j < Grille.getTailleDefaut() ; j++){
                    canvas.drawText(this.grille.getGrilleCaracteres()[i][j],departX + decalageX + 30 , departY + decalageY - 30 ,this.paint);
                    decalageY += 120;
                }
                decalageY = 0;
                decalageX += 120;
            }
            paint2.setStrokeJoin(Paint.Join.ROUND);
            paint2.setStyle(Paint.Style.STROKE);
            paint2.setStrokeWidth(5f);
            canvas.drawPath(path,paint2);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float xPos = event.getX();
        float yPos = event.getY();
        String motRecupere;

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(coordDansLaGrille(xPos,yPos)) {
                    path.moveTo(xPos - (xPos % 120), yPos - (yPos % 120) + 60);
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                if(coordDansLaGrille(xPos,yPos)) {
                    path.lineTo(xPos - (xPos % 120), yPos - (yPos % 120) + 60);
                    System.out.println(getLettreGrille(xPos,yPos));
                }
                /*System.out.println("x : " + xPos);
                System.out.println("y : " + yPos);*/
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                return false;
        }
        invalidate();
        return true;
    }

    public boolean coordDansLaGrille(float xPos,float yPos){
        return xPos >= 200 && yPos >= 0 && xPos <= 1400 && yPos < 1440;
    }

    public String getLettreGrille(float xPos,float yPos){
        int xPosModulo,yPosModulo;
        xPosModulo = ((int) (xPos-200)/120);
        yPosModulo = ((int) yPos/120);
        return this.grille.getGrilleCaracteres()[xPosModulo][yPosModulo];
    }

}
