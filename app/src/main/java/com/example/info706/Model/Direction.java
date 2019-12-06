package com.example.info706.Model;

import java.util.Random;

public enum Direction {
    HORIZONTAL,
    VERTICAL,
    DIAGONAL_BAS,
    DIAGONAL_HAUT
    ;

    public static Direction randomDirection(){
        return values()[new Random().nextInt(values().length)];
    }


}
