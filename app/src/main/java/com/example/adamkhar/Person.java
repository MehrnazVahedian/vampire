package com.example.adamkhar;

public class Person {

    public Person(int view, Positions position, boolean isVampire){
        this.isVampire = isVampire;
        this.position = position;
        this.view = view;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public Positions getPosition() {
        return position;
    }

    public void setPosition(Positions position) {
        this.position = position;
    }

    public boolean isVampire() {
        return isVampire;
    }

    public void setVampire(boolean vampire) {
        isVampire = vampire;
    }

    private int view;
    private Positions position;
    private boolean isVampire;

}
