package com.example.info706.Model;

public class Trait {
    public static final int TAILLE_TABLEAU = 4;
    private int color;
    private float tabPoints[];

    public Trait(int color){
        this.color = color;
        this.tabPoints = new float[TAILLE_TABLEAU];
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public float[] getTabPoints() {
        return tabPoints;
    }

    public void setTabPoints(float[] tabPoints) {
        this.tabPoints = tabPoints;
    }
}
