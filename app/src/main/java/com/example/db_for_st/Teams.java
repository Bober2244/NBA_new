package com.example.db_for_st;

import android.widget.EditText;

import java.util.ArrayList;

public class Teams {
    public static ArrayList<String> team_ = new ArrayList<>();
    public Teams(){

    }
    public void addTeam(EditText s){
        team_.add(s.getText().toString());
    }
}
