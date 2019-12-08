package com.example.info706.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

import com.example.info706.Model.Grille;
import com.example.info706.Model.Mot;
import com.example.info706.Model.Trait;
import com.example.info706.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;


public class CanvasGrille extends View {

    public final static int DECALAGE_X = 120;
    public final static int DECALAGE_Y = 120;
    public final static int DECALAGE_DEPART_X = 75;
    public final static int DECALAGE_DEPART_Y = 120;
    private Paint paint;
    private Paint paint2;
    private Grille grille;
    private Path path;
    private int xPosDepart, yPosDepart, xPosFin, yPosFin;
    private float xPosDepartFloat, yPosDepartFloat, xPosFinFloat, yPosFinFloat;
    private ArrayList<String> listMotRaye;
    private String motRecupere;
    private ArrayList<Trait> listTrait;
    MainActivity mainActivity;


    public CanvasGrille(Context context, Grille grille) {
        super(context);
        this.paint = new Paint();
        this.paint2 = new Paint();
        this.path = new Path();
        this.listTrait = new ArrayList<>();
        this.listMotRaye = new ArrayList<>();
        this.grille = grille;
        this.paint2.setAntiAlias(true);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(getResources().getColor(R.color.colorBackground));
        this.paint.setColor(Color.WHITE);
        this.paint.setTextSize(60);
        int departX = CanvasGrille.DECALAGE_DEPART_X;
        int departY = CanvasGrille.DECALAGE_DEPART_Y;
        int decalageX = 0;
        int decalageY = 0;

        int i, j;
        for (i = 0; i < Grille.getLargeurDefaut(); i++) {
            for (j = 0; j < Grille.getHauteurDefaut(); j++) {
                canvas.drawText(this.grille.getGrilleCaracteres()[i][j], departX + decalageX + 30, departY + decalageY - 30, this.paint);
                decalageY += CanvasGrille.DECALAGE_Y;
            }
            decalageY = 0;
            decalageX += CanvasGrille.DECALAGE_X;
        }
        this.dessinerMotsRayes(this.listTrait, this.paint2, canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float xPos = event.getX();
        float yPos = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                this.motRecupere = "";
                if (coordDansLaGrille(xPos, yPos)) {
                    xPosDepart = ((int) (xPos - CanvasGrille.DECALAGE_DEPART_X) / CanvasGrille.DECALAGE_X);
                    yPosDepart = ((int) yPos / CanvasGrille.DECALAGE_Y);
                    xPosDepartFloat = (((xPosDepart + 1)) * CanvasGrille.DECALAGE_X);
                    yPosDepartFloat = ((yPosDepart + 1) * CanvasGrille.DECALAGE_Y) - 60;
                    System.out.println("xPosDepart : " + xPosDepart);
                    System.out.println("yPosDepart : " + yPosDepart);
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                if (coordDansLaGrille(xPos, yPos)) {
                    xPosFin = ((int) (xPos - CanvasGrille.DECALAGE_DEPART_X) / CanvasGrille.DECALAGE_X);
                    yPosFin = ((int) yPos / CanvasGrille.DECALAGE_Y);
                    System.out.println("xPosFin: " + xPosFin);
                    System.out.println("yPosFin: " + yPosFin);
                    if (xPosDepart != xPosFin || yPosDepart != yPosFin) {
                        this.motRecupere = recupererMot(xPosDepart, yPosDepart, xPosFin, yPosFin);
                        if (motDansLaGrille(motRecupere) && !motDejaRaye(motRecupere)) {
                            listMotRaye.add(motRecupere);
                            xPosFinFloat = (((xPosFin + 1)) * CanvasGrille.DECALAGE_X);
                            yPosFinFloat = ((yPosFin + 1) * CanvasGrille.DECALAGE_Y) - 60;
                            float tabPoints[] = {xPosDepartFloat, yPosDepartFloat, xPosFinFloat, yPosFinFloat};
                            Trait trait = new Trait(this.randomColor());
                            trait.setTabPoints(tabPoints);
                            this.mainActivity.griseItemListView(this.indexMotDansLaGrille(motRecupere), trait.getColor());
                            listTrait.add(trait);
                        }
                    }
                }
                break;
            default:
                return false;
        }

        invalidate();
        return true;
    }

    public void dessinerMotsRayes(ArrayList<Trait> listTrait, Paint paint, Canvas canvas) {
        Iterator<Trait> iteratorTrait = listTrait.iterator();
        while (iteratorTrait.hasNext()) {
            Trait traitCourant = iteratorTrait.next();
            paint.setColor(traitCourant.getColor());
            paint.setAlpha(200);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(42f);
            canvas.drawLine(traitCourant.getTabPoints()[0], traitCourant.getTabPoints()[1], traitCourant.getTabPoints()[2], traitCourant.getTabPoints()[3], paint);
        }
    }

    public boolean coordDansLaGrille(float xPos, float yPos) {
        return xPos >= CanvasGrille.DECALAGE_DEPART_X && yPos >= 0 && xPos <= (CanvasGrille.DECALAGE_X * Grille.getLargeurDefaut()) + CanvasGrille.DECALAGE_DEPART_X && (yPos < CanvasGrille.DECALAGE_Y * Grille.getHauteurDefaut() + 120);
    }

    public boolean motDansLaGrille(String mot) {
        boolean resultat = false;
        Iterator<Mot> iterator = this.grille.getListeMotsFinale().iterator();
        while (iterator.hasNext()) {
            Mot motCourant = iterator.next();
            if (motCourant.getChaineMot().equals(mot)) {
                resultat = true;
            }
        }
        return resultat;
    }

    public boolean motDejaRaye(String mot){
        boolean raye = false;
        Iterator<String> iteratorListMotRaye = this.listMotRaye.iterator();
        while(iteratorListMotRaye.hasNext()){
            String motIterator = iteratorListMotRaye.next();
            if(motIterator.equals(mot)){
                raye = true;
            }
        }
        return raye;
    }

    public int indexMotDansLaGrille(String mot) {
        int resultat = 0;
        int index = 0;
        Iterator<Mot> iterator = this.grille.getListeMotsFinale().iterator();
        while (iterator.hasNext()) {
            Mot motCourant = iterator.next();
            if (motCourant.getChaineMot().equals(mot)) {
                resultat = index;
            }
            index++;
        }
        return resultat;
    }

    public String recupererMot(int xPosDepart, int yPosDepart, int xPosFin, int yPosFin) {
        String resultat = "";
        if (xPosDepart == xPosFin && yPosDepart <= yPosFin) {
            for (int j = yPosDepart; j < yPosFin; j++) {
                resultat += "" + this.grille.getGrilleCaracteres()[xPosDepart][j];
            }
        }

        if (xPosDepart == xPosFin && yPosDepart > yPosFin) {
            for (int j = yPosDepart; j > yPosFin; j--) {
                resultat += "" + this.grille.getGrilleCaracteres()[xPosDepart][j];
            }
        }

        if (xPosDepart <= xPosFin && yPosDepart == yPosFin) {
            for (int i = xPosDepart; i < xPosFin; i++) {
                resultat += "" + this.grille.getGrilleCaracteres()[i][yPosDepart];
            }
        }

        if (xPosDepart > xPosFin && yPosDepart == yPosFin) {
            for (int i = xPosDepart; i > xPosFin; i--) {
                resultat += "" + this.grille.getGrilleCaracteres()[i][yPosDepart];
            }
        }

        if (xPosFin - xPosDepart == yPosFin - yPosDepart) {
            if (xPosDepart < xPosFin && yPosDepart < yPosFin) {
                for (int i = 0; i < xPosFin - xPosDepart; i++) {
                    resultat += "" + this.grille.getGrilleCaracteres()[i + xPosDepart][i + yPosDepart];
                }
            }
        }
        if (xPosFin - xPosDepart == yPosFin - yPosDepart) {
            if (xPosDepart > xPosFin && yPosDepart > yPosFin) {
                for (int i = 0; i > xPosFin - xPosDepart; i--) {
                    resultat += "" + this.grille.getGrilleCaracteres()[i + xPosDepart][i + yPosDepart];
                }
            }
        }

        if (-(xPosFin - xPosDepart) == yPosFin - yPosDepart) {
            if (xPosDepart > xPosFin && yPosDepart < yPosFin) {
                for (int i = 0; i > xPosFin - xPosDepart; i--) {
                    resultat += "" + this.grille.getGrilleCaracteres()[i + xPosDepart][-i + yPosDepart];
                }
            }
        }

        if (xPosFin - xPosDepart == -(yPosFin - yPosDepart)) {
            if (xPosDepart < xPosFin && yPosDepart > yPosFin) {
                for (int i = 0; i < xPosFin - xPosDepart; i++) {
                    resultat += "" + this.grille.getGrilleCaracteres()[i + xPosDepart][-i + yPosDepart];
                }
            }
        }
        return resultat;
    }


    public int randomColor() {
        Random random = new Random();
        int color = Color.argb(255, random.nextInt(80) + 120, random.nextInt(80) + 120, random.nextInt(80) + 120);
        return color;
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }
}