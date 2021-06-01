package com.example.db_for_st;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;


public class AddTeams extends Activity {
    EditText com;
    Button b1;
    Button b2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_teams);
        com=(EditText) findViewById(R.id.command);
        b1= (Button) findViewById(R.id.button);
        b2= (Button) findViewById(R.id.button2);

        Teams t = new Teams();
        ArrayList<String> a = t.team_;
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t.addTeam(com);
                finish();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
