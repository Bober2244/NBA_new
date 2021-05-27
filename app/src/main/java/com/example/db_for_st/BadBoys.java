package com.example.db_for_st;

import java.io.Serializable;

public class BadBoys implements Serializable {
    private  long id;
    private  String badboy;
    private  int goalshouse;
    private  int goalsguest;

    public BadBoys(long id, String badboy, int goalshouse, int goalsguest) {
        this.id = id;
        this.badboy = badboy;
        this.goalshouse = goalshouse;
        this.goalsguest = goalsguest;
    }
    public long getId() {
        return id;
    }

    public String getBadboy() {
        return badboy;
    }

    public int getGoalshouse() {
        return goalshouse;
    }

    public int getGoalsguest() {
        return goalsguest;
    }
}
