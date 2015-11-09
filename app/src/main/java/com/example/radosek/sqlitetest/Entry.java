package com.example.radosek.sqlitetest;

/**
 * Created by smuggler on 10.11.15.
 */
public class Entry {
    public int id;
    public String name;

    public Entry(int id, String name){
        this.name = name;
        this.id = id;
    }

    @Override
    public String toString() {
        return name;
    }
}
