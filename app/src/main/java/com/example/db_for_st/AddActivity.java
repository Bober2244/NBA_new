package com.example.db_for_st;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class AddActivity extends Activity {
    private Button btSave,btCancel;
    private TextView text1, text2;
    private EditText etGoalsHome,etGoalsGuest;
    private Spinner spinner1,spinner2;
    private long MyMatchID;

    Teams t = new Teams();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        text1 = (TextView) findViewById(R.id.inviz1);
        text2 = (TextView) findViewById(R.id.inviz2);
        text1.setVisibility(View.GONE);
        text2.setVisibility(View.GONE);

        etGoalsHome=(EditText)findViewById(R.id.GoalsHome);
        etGoalsGuest=(EditText)findViewById(R.id.GoalsGuest);
        btSave=(Button)findViewById(R.id.butSave);
        btCancel=(Button)findViewById(R.id.butCancel);
        t.team_.add("Warriors");
        t.team_.add("Blazers");
        t.team_.add("Hawks");
        t.team_.add("Lakers");
        t.team_.add("Clippers");
        t.team_.add("Celtics");
        t.team_.add("Jazz");
        t.team_.add("Suns");
        t.team_.add("Nuggets");
        t.team_.add("Bulls");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, t.team_);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner2=(Spinner)findViewById(R.id.TeamGuest);
        spinner1=(Spinner)findViewById(R.id.TeamHome);
        spinner1.setAdapter(adapter);
        spinner2.setAdapter(adapter);
        spinner1.setPrompt("Команда хозяев");
        spinner2.setPrompt("Команда гостей");
        spinner1.setSelection(0);
        spinner2.setSelection(0);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                text1.setText(spinner1.getSelectedItem().toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                text2.setText(spinner2.getSelectedItem().toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        if(getIntent().hasExtra("Matches")){
            Matches matches=(Matches)getIntent().getSerializableExtra("Matches");
            MyMatchID=matches.getId();
        }
        else
        {
            MyMatchID=-1;
        }
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Matches matches=new Matches(MyMatchID, text1.getText().toString(),
                        text2.getText().toString(),
                        Integer.parseInt(etGoalsHome.getText().toString()),
                        Integer.parseInt(etGoalsGuest.getText().toString()));
                Intent intent=getIntent();
                intent.putExtra("Matches",matches);
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
    @Override
    protected void onDestroy() {
        super.onDestroy();
        t.team_.remove("Warriors");
        t.team_.remove("Blazers");
        t.team_.remove("Hawks");
        t.team_.remove("Lakers");
        t.team_.remove("Clippers");
        t.team_.remove("Celtics");
        t.team_.remove("Jazz");
        t.team_.remove("Suns");
        t.team_.remove("Nuggets");
        t.team_.remove("Bulls");
    }
}