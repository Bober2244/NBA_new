package com.example.db_for_st;

import java.io.Serializable;

public class Teams implements Serializable {
    private long id;
    private String teamhouse;
    private double goalsguest;

    public Teams(long id, String teamh, double gg) {
        this.id = id;
        this.teamhouse = teamh;
        this.goalsguest=gg;
    }

    public long getId() {
        return id;
    }

    public String getHouse() {
        return teamhouse;
    }

   public double getGuest() {
        return goalsguest;
    }
}