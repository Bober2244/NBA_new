package com.example.db_for_st;

import java.io.Serializable;

public class Teams implements Serializable {
    private long id;
    private String team;
    private int rating;

    public Teams(long id, String team /*сюда int rating, но потом*/) {
        this.id = id;
        this.team = team;
    }

    public long getId() {
        return id;
    }

    public String getTeam() {
        return team;
    }

    //public int getRating() {return rating;}
}