package com.example.info706.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.View;

import com.example.info706.Model.Grille;
import com.example.info706.Model.Mot;
import com.example.info706.Model.Partie;
import com.example.info706.Model.Trait;
import com.example.info706.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Classe de l'activité représentant la grille de lettres
 * @author Brozzoni Vincent / Jugand Théo
 */
public class CanvasGrille extends View {

    /**
     * Constante du décalage en abscisse entre chaque lettre
     */
    public final static int DECALAGE_X = 120;
    /**
     * Constante du décalage en ordonnée entre chaque lettre
     */
    public final static int DECALAGE_Y = 120;
    /**
     * Constante de la coordonnée x de départ du canvas
     */
    public final static int DECALAGE_DEPART_X = 75;
    /**
     * Constante de la coordonnée y de départ du canvas
     */
    public final static int DECALAGE_DEPART_Y = 120;
    /**
     * Instance de la classe Paint permettant de dessiner les lettres dans la grille
     */
    private Paint paintLettre;
    /**
     * Instance de la classe Paint permettant de dessiner les traits dans la grille
     */
    private Paint paintTrait;
    private Grille grille;
    private Path path;
    private int xPosDepart, yPosDepart, xPosFin, yPosFin;
    private float xPosDepartFloat, yPosDepartFloat, xPosFinFloat, yPosFinFloat;
    private String motRecupere;
    private ArrayList<Trait> listTrait;
    private MainActivity mainActivity;
    private Partie partie;

    public CanvasGrille(Context context, Grille grille,MainActivity mainActivity, Partie partie) {
        super(context);
        this.paintLettre = new Paint();
        this.paintTrait = new Paint();
        this.path = new Path();
        this.listTrait = new ArrayList<>();
        this.grille = grille;
        this.paintTrait.setAntiAlias(true);
        this.mainActivity = mainActivity;
        this.partie = partie;
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(getResources().getColor(R.color.colorBackground));
        this.paintLettre.setColor(Color.WHITE);
        this.paintLettre.setTextSize(60);
        int departX = CanvasGrille.DECALAGE_DEPART_X;
        int departY = CanvasGrille.DECALAGE_DEPART_Y;
        int decalageX = 0;
        int decalageY = 0;
        int i, j;
        for (i = 0; i < Grille.getLargeurDefaut(); i++) {
            for (j = 0; j < Grille.getHauteurDefaut(); j++) {
                canvas.drawText(this.grille.getGrilleCaracteres()[i][j], departX + decalageX + 30, departY + decalageY - 30, this.paintLettre);
                decalageY += CanvasGrille.DECALAGE_Y;
            }
            decalageY = 0;
            decalageX += CanvasGrille.DECALAGE_X;
        }
        this.dessinerMotsRayes(this.listTrait, this.paintTrait, canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float xPos = event.getX();
        float yPos = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                this.motRecupere = "";
                if (coordDansLaGrille(xPos, yPos)) {
                    this.xPosDepart = ((int) (xPos - CanvasGrille.DECALAGE_DEPART_X) / CanvasGrille.DECALAGE_X);
                    this.yPosDepart = ((int) yPos / CanvasGrille.DECALAGE_Y);
                    this.xPosDepartFloat = (((this.xPosDepart + 1)) * CanvasGrille.DECALAGE_X);
                    this.yPosDepartFloat = ((this.yPosDepart + 1) * CanvasGrille.DECALAGE_Y) - 60;
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                if (coordDansLaGrille(xPos, yPos)) {
                    this.xPosFin = ((int) (xPos - CanvasGrille.DECALAGE_DEPART_X) / CanvasGrille.DECALAGE_X);
                    this.yPosFin = ((int) yPos / CanvasGrille.DECALAGE_Y);
                    if (this.xPosDepart != this.xPosFin || this.yPosDepart != this.yPosFin) {
                        this.motRecupere = recupererMot(this.xPosDepart, this.yPosDepart, this.xPosFin, this.yPosFin);
                        if (motValide(this.motRecupere)) {
                            this.xPosFinFloat = (((this.xPosFin + 1)) * CanvasGrille.DECALAGE_X);
                            this.yPosFinFloat = ((this.yPosFin + 1) * CanvasGrille.DECALAGE_Y) - 60;
                            float tabPoints[] = {this.xPosDepartFloat, this.yPosDepartFloat, this.xPosFinFloat, this.yPosFinFloat};
                            Trait trait = new Trait(this.randomColor());
                            trait.setTabPoints(tabPoints);
                            this.mainActivity.griseItemListView(this.indexMotDansLaGrille(motRecupere), trait.getColor());
                            this.mainActivity.afficheDefinitionMot(this.grille.getMotByString(motRecupere));
                            this.listTrait.add(trait);
                            this.partie.finPartie();
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
        return xPos >= CanvasGrille.DECALAGE_DEPART_X && yPos >= 0 && xPos <= (CanvasGrille.DECALAGE_X * Grille.getLargeurDefaut()) + CanvasGrille.DECALAGE_DEPART_X && yPos <= (CanvasGrille.DECALAGE_Y * Grille.getHauteurDefaut());
    }

    public boolean motValide(String mot) {
        boolean resultat = false;
        Iterator<Mot> iterator = this.grille.getListeMotsFinale().iterator();
        while (iterator.hasNext()) {
            Mot motCourant = iterator.next();
            if (motCourant.getChaineMot().equals(mot) && !motCourant.getTrouve()) {
                resultat = true;
                motCourant.setTrouve(resultat);
            }
        }
        return resultat;
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
            for (int j = yPosDepart; j <= yPosFin; j++) {
                resultat += "" + this.grille.getGrilleCaracteres()[xPosDepart][j];
            }
        }

        if (xPosDepart == xPosFin && yPosDepart > yPosFin) {
            for (int j = yPosDepart; j >= yPosFin; j--) {
                resultat += "" + this.grille.getGrilleCaracteres()[xPosDepart][j];
            }
        }

        if (xPosDepart <= xPosFin && yPosDepart == yPosFin) {
            for (int i = xPosDepart; i <= xPosFin; i++) {
                resultat += "" + this.grille.getGrilleCaracteres()[i][yPosDepart];
            }
        }

        if (xPosDepart > xPosFin && yPosDepart == yPosFin) {
            for (int i = xPosDepart; i >= xPosFin; i--) {
                resultat += "" + this.grille.getGrilleCaracteres()[i][yPosDepart];
            }
        }

        if (xPosFin - xPosDepart == yPosFin - yPosDepart) {
            if (xPosDepart < xPosFin && yPosDepart < yPosFin) {
                for (int i = 0; i <= xPosFin - xPosDepart; i++) {
                    resultat += "" + this.grille.getGrilleCaracteres()[i + xPosDepart][i + yPosDepart];
                }
            }
        }
        if (xPosFin - xPosDepart == yPosFin - yPosDepart) {
            if (xPosDepart > xPosFin && yPosDepart > yPosFin) {
                for (int i = 0; i >= xPosFin - xPosDepart; i--) {
                    resultat += "" + this.grille.getGrilleCaracteres()[i + xPosDepart][i + yPosDepart];
                }
            }
        }

        if (-(xPosFin - xPosDepart) == yPosFin - yPosDepart) {
            if (xPosDepart > xPosFin && yPosDepart < yPosFin) {
                for (int i = 0; i >= xPosFin - xPosDepart; i--) {
                    resultat += "" + this.grille.getGrilleCaracteres()[i + xPosDepart][-i + yPosDepart];
                }
            }
        }

        if (xPosFin - xPosDepart == -(yPosFin - yPosDepart)) {
            if (xPosDepart < xPosFin && yPosDepart > yPosFin) {
                for (int i = 0; i <= xPosFin - xPosDepart; i++) {
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
}