package com.example.info706.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

import com.example.info706.Model.Grille;
import com.example.info706.Model.Mot;
import com.example.info706.R;

import java.util.Iterator;


public class CanvasGrille extends View {

    private Paint paint;
    private Paint paint2;
    private Grille grille;
    private Path path;
    private int xPosDepart, yPosDepart, xPosFin, yPosFin;
    private String motRecupere;
    MainActivity mainActivity;


    public CanvasGrille(Context context, Grille grille) {
        super(context);
        this.paint = new Paint();
        this.paint2 = new Paint();
        this.path = new Path();
        this.grille = grille;
        paint2.setAntiAlias(true);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(getResources().getColor(R.color.colorBackground));
        this.paint.setColor(Color.WHITE);
        this.paint.setTextSize(60);
        int departX = 200;
        int departY = 120;
        int decalageX = 0;
        int decalageY = 0;

        int i, j;
        for (i = 0; i < Grille.getLargeurDefaut(); i++) {
            for (j = 0; j < Grille.getHauteurDefaut(); j++) {
                canvas.drawText(this.grille.getGrilleCaracteres()[i][j], departX + decalageX + 30, departY + decalageY - 30, this.paint);
                decalageY += 120;
            }
            decalageY = 0;
            decalageX += 120;
        }
        this.paint2.setColor(Color.WHITE);
        this.paint2.setStrokeJoin(Paint.Join.ROUND);
        this.paint2.setStyle(Paint.Style.STROKE);
        this.paint2.setStrokeWidth(8f);
        canvas.drawPath(path, paint2);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float xPos = event.getX();
        float yPos = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                this.motRecupere = "";
                if (coordDansLaGrille(xPos, yPos)) {
                    xPosDepart = ((int) (xPos - 200) / 120);
                    yPosDepart = ((int) yPos / 120);
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                xPosFin = ((int) (xPos - 200) / 120);
                yPosFin = ((int) yPos / 120);
                if (xPosDepart != xPosFin || yPosDepart != yPosFin) {
                    this.motRecupere = recupererMot(xPosDepart,yPosDepart,xPosFin,yPosFin);
                    if (motDansLaGrille(motRecupere)) {
                        System.out.println("APPLICATION TERMINEE !!!!!!!!!!!!!!!");
                    }
                }

                break;
            default:
                return false;
        }

        invalidate();
        return true;
    }

    public boolean coordDansLaGrille(float xPos, float yPos) {
        return xPos >= 200 && yPos >= 0 && xPos <= 1400 && yPos < 1320;
    }

    public boolean motDansLaGrille(String mot) {
        boolean resultat = false;
        int index = 0;
        Iterator<Mot> iterator = this.grille.getListeMotsFinale().iterator();
        while (iterator.hasNext()) {
            Mot motCourant = iterator.next();
            if (motCourant.getChaineMot().equals(mot)) {
                resultat = true;
                this.mainActivity.griseItemListView(index);
            }
            index++;
        }
        return resultat;
    }

    public String recupererMot(int xPosDepart, int yPosDepart, int xPosFin, int yPosFin) {
        String resultat = "";
        if (xPosDepart == xPosFin && yPosDepart <= yPosFin) {
            for (int j = yPosDepart; j <= yPosFin; j++) {
                resultat += "" + this.grille.getGrilleCaracteres()[xPosDepart][j];
            }
        }
        System.out.println("Mot: " + this.motRecupere);

        if (xPosDepart == xPosFin && yPosDepart > yPosFin) {
            for (int j = yPosDepart; j >= yPosFin; j--) {
                resultat += "" + this.grille.getGrilleCaracteres()[xPosDepart][j];
            }
            System.out.println("Mot: " + motRecupere);
        }

        if (xPosDepart <= xPosFin && yPosDepart == yPosFin) {
            for (int i = xPosDepart; i <= xPosFin; i++) {
                resultat += "" + this.grille.getGrilleCaracteres()[i][yPosDepart];
            }
            System.out.println("Mot: " + motRecupere);
        }

        if (xPosDepart > xPosFin && yPosDepart == yPosFin) {
            for (int i = xPosDepart; i >= xPosFin; i--) {
                resultat += "" + this.grille.getGrilleCaracteres()[i][yPosDepart];
            }
        }
        return resultat;
    }

    public String getLettreGrille(float xPos, float yPos) {
        int xPosModulo, yPosModulo;
        xPosModulo = ((int) (xPos - 200) / 120);
        yPosModulo = ((int) yPos / 120);
        return this.grille.getGrilleCaracteres()[xPosModulo][yPosModulo];
    }

    public void setMainActivity(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }
}
