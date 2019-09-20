package com.example.info706.Model;

import java.util.Random;

public enum Sens {
    DROIT,
    INVERSE;

    public static Sens randomSens(){
        return values()[new Random().nextInt(values().length)];
    }
}
