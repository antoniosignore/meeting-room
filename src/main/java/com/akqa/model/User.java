package com.akqa.model;

import java.util.Observable;
import java.util.Observer;

public final class User implements Observer, PrintableAsJson {

    private final String name;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("o = " + o);
    }
}
