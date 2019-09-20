package com.example.info706.Model;

import java.util.Random;

public enum Direction {
    HORIZONTAL,
    VERTICAL;

    public static Direction randomDirection(){
        return values()[new Random().nextInt(values().length)];
    }


}
