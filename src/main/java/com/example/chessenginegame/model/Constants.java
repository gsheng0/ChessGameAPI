package com.example.chessenginegame.model;

public class Constants {
    public final static Integer         UP  = -8;
    public final static Integer       DOWN  =  8;
    public final static Integer       LEFT  = -1;
    public final static Integer      RIGHT  =  1;
    public final static Integer  LEFT_DOWN  =  7;
    public final static Integer    LEFT_UP  = -9;
    public final static Integer RIGHT_DOWN  =  9;
    public final static Integer   RIGHT_UP  = -7;

    public final static Integer LEFT2_DOWN  =   6;
    public final static Integer LEFT_DOWN2  =  15;
    public final static Integer   LEFT2_UP  = -10;
    public final static Integer   LEFT_UP2  = -17;

    public final static Integer RIGHT2_DOWN  =  10;
    public final static Integer RIGHT_DOWN2  =  17;
    public final static Integer   RIGHT2_UP  =  -6;
    public final static Integer   RIGHT_UP2  = -15;

    public static final String WHITE = "WHITE";
    public static final String BLACK = "BLACK";

    public static final int NO_CHECK = 1;
    public static final int CHECK = 2;
    public static int DOUBLE_CHECK;
}