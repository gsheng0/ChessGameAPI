package com.example.chessenginegame.util;

public class Tuple<A, B>{
    private A a;
    private B b;
    private Tuple(A a, B b){
        this.a = a;
        this.b = b;
    }
    public static <A, B> Tuple<A, B> of(A a, B b){
        return new Tuple<>(a, b);
    }
    public A getFirst(){
        return a;
    }
    public B getSecond(){
        return b;
    }
}
