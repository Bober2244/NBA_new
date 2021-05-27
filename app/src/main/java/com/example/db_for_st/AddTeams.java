package com.example.db_for_st;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;



public class AddTeams extends Activity {
    private Button btSave,btCancel;
    private EditText etTeam;
    private long MyTeamID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_team);

        etTeam=(EditText)findViewById(R.id.editteam);
        btSave=(Button)findViewById(R.id.button);
        btCancel=(Button)findViewById(R.id.button2);

        if(getIntent().hasExtra("Teams")){
            Teams teams=(Teams) getIntent().getSerializableExtra("Teams");
            etTeam.setText(teams.getTeam());
            MyTeamID=teams.getId();
        }
        else
        {
            MyTeamID=-1;
        }

        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Teams teams=new Teams(MyTeamID,etTeam.getText().toString());
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
    public EditText c1(){
        return etTeam;
    }
}