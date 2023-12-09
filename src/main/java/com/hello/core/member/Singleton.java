package com.hello.core.member;

public class Singleton {

    private Singleton() {
    }

    public int test;
    private static final Singleton singleton = new Singleton();

    public static Singleton getInstance() {
        return singleton;
    }
}
