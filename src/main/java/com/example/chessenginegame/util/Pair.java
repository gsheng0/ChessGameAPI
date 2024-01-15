package com.example.chessenginegame.util;

public class Pair<A, B>{
    private A a;
    private B b;
    private Pair(A a, B b){
        this.a = a;
        this.b = b;
    }
    public static <A, B> Pair<A, B> of(A a, B b){
        return new Pair<>(a, b);
    }
    public A left(){
        return a;
    }
    public B right(){
        return b;
    }
}
