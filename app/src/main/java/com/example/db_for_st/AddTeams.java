package com.example.db_for_st;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddTeams extends Activity {
    private Button btSave,btCancel;
    private EditText etRating, etTeam;
    private long MyMatchID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_team);

        etTeam=(EditText)findViewById(R.id.etTeam);
        etRating=(EditText)findViewById(R.id.etRating);
        btSave=(Button)findViewById(R.id.but1);
        btCancel=(Button)findViewById(R.id.but2);

        if(getIntent().hasExtra("Teams")){
            Teams teams=(Teams) getIntent().getSerializableExtra("Teams");
            etTeam.setText(teams.getHouse());
            etRating.setText(Double.toString(teams.getGuest()));
            MyMatchID=teams.getId();
        }
        else
        {
            MyMatchID=-1;
        }
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Teams teams=new Teams(MyMatchID,etTeam.getText().toString(), Double.parseDouble(etRating.getText().toString()));
                Intent intent=getIntent();
                intent.putExtra("Teams",teams);
                setResult(RESULT_OK,intent);
                finish();
            }
        });

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
